package br.beanascigom.testesoftplan.service;

import br.beanascigom.testesoftplan.dto.PessoaRequestDTO;
import br.beanascigom.testesoftplan.dto.PessoaResponseDTO;
import br.beanascigom.testesoftplan.exception.BusinessConflictException;
import br.beanascigom.testesoftplan.exception.BusinessNotFoundException;
import br.beanascigom.testesoftplan.model.Pessoa;
import br.beanascigom.testesoftplan.model.Sexo;
import br.beanascigom.testesoftplan.repository.PessoaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService service;

    @Mock
    private PessoaRepository repository;

    @Mock
    private ObjectMapper mapper;

    private PessoaRequestDTO request;
    private Pessoa pessoa;

    @BeforeEach
    void setup() {
        request = PessoaRequestDTO.builder()
                .nome("Maria")
                .sexo(Sexo.M)
                .email("maria@email.com")
                .dataNascimento(LocalDate.of(1990, 4, 15))
                .estado("SC")
                .pais("Brasil")
                .cpf("123.456.789-09")
                .build();

        pessoa = Pessoa.builder()
                .id(1L)
                .nome("Maria")
                .sexo(Sexo.M)
                .email("maria@email.com")
                .dataNascimento(LocalDate.of(1990, 4, 15))
                .estado("SC")
                .pais("Brasil")
                .cpf("12345678909")
                .dataCriacao(OffsetDateTime.now())
                .dataAtualizacao(OffsetDateTime.now())
                .build();
    }

    @Test
    void criar_deveSalvarPessoaComCpfNormalizado() throws JsonProcessingException {
        when(repository.existsByCpf("12345678909")).thenReturn(false);
        when(repository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaResponseDTO response = service.criar(request);

        ArgumentCaptor<Pessoa> captor = ArgumentCaptor.forClass(Pessoa.class);
        verify(repository).save(captor.capture());
        Pessoa salva = captor.getValue();

        assertEquals("12345678909", salva.getCpf());
        assertEquals("Maria", response.getNome());
        assertEquals("12345678909", response.getCpf());
    }

    @Test
    void criar_quandoCpfDuplicado_deveLancarExcecao() {
        when(repository.existsByCpf("12345678909")).thenReturn(true);

        assertThrows(BusinessConflictException.class, () -> service.criar(request));

        verify(repository, never()).save(any(Pessoa.class));
    }

    @Test
    void atualizar_deveAtualizarPessoaComCpfNormalizado() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(repository.existsByCpfAndIdNot("12345678909", 1L)).thenReturn(false);
        when(repository.save(any(Pessoa.class))).thenReturn(pessoa);

        PessoaResponseDTO response = service.atualizar(1L, request);

        assertEquals(1L, response.getId());
        assertEquals("12345678909", response.getCpf());
        verify(repository).save(pessoa);
    }

    @Test
    void atualizar_quandoPessoaNaoEncontrada_deveLancarExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessNotFoundException.class, () -> service.atualizar(1L, request));
    }

    @Test
    void atualizar_quandoCpfDuplicado_deveLancarExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));
        when(repository.existsByCpfAndIdNot("12345678909", 1L)).thenReturn(true);

        assertThrows(BusinessConflictException.class, () -> service.atualizar(1L, request));

        verify(repository, never()).save(any(Pessoa.class));
    }

    @Test
    void buscaPessoaPorId_quandoEncontrada_deveRetornarResponse() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        PessoaResponseDTO response = service.buscaPessoaPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Maria", response.getNome());
    }

    @Test
    void buscaPessoaPorId_quandoNaoEncontrada_deveLancarExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessNotFoundException.class, () -> service.buscaPessoaPorId(1L));
    }

    @Test
    void listarPessoas_deveRetornarListaMapeada() {
        when(repository.findAll()).thenReturn(List.of(pessoa));

        List<PessoaResponseDTO> response = service.listarPessoas();

        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals("12345678909", response.get(0).getCpf());
    }
}
