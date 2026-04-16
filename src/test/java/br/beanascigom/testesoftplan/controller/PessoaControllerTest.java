package br.beanascigom.testesoftplan.controller;

import br.beanascigom.testesoftplan.dto.PessoaRequestDTO;
import br.beanascigom.testesoftplan.dto.PessoaResponseDTO;
import br.beanascigom.testesoftplan.exception.BusinessNotFoundException;
import br.beanascigom.testesoftplan.exception.RestExceptionHandler;
import br.beanascigom.testesoftplan.model.Sexo;
import br.beanascigom.testesoftplan.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        PessoaController pessoaController = new PessoaController();
        ReflectionTestUtils.setField(pessoaController, "service", pessoaService);

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(pessoaController)
                .setControllerAdvice(new RestExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @Test
    void criar_quandoRequestValido_deveRetornarCreated() throws Exception {
        PessoaResponseDTO response = PessoaResponseDTO.builder()
                .id(1L)
                .nome("Maria")
                .sexo(Sexo.M)
                .email("maria@email.com")
                .dataNascimento(LocalDate.of(1990, 4, 15))
                .estado("SC")
                .pais("Brasil")
                .cpf("52998224725")
                .dataCriacao(OffsetDateTime.parse("2026-04-15T20:00:00-03:00"))
                .dataAtualizacao(OffsetDateTime.parse("2026-04-15T20:10:00-03:00"))
                .build();

        when(pessoaService.criar(any(PessoaRequestDTO.class))).thenReturn(response);

        String payload = """
                {
                  "nome": "Maria",
                  "sexo": "M",
                  "email": "maria@email.com",
                  "dataNascimento": "15/04/1990",
                  "estado": "SC",
                  "pais": "Brasil",
                  "cpf": "529.982.247-25"
                }
                """;

        mockMvc.perform(post("/api/v1/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria"))
                .andExpect(jsonPath("$.dataNascimento").value("15/04/1990"));

        ArgumentCaptor<PessoaRequestDTO> captor = ArgumentCaptor.forClass(PessoaRequestDTO.class);
        verify(pessoaService).criar(captor.capture());
        assertEquals("529.982.247-25", captor.getValue().getCpf());
    }

    @Test
    void criar_quandoRequestInvalido_deveRetornarBadRequest() throws Exception {
        String payload = """
                {
                  "email": "email-invalido",
                  "dataNascimento": "15/04/2999",
                  "cpf": ""
                }
                """;

        mockMvc.perform(post("/api/v1/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Falha de validacao no corpo da requisicao"))
                .andExpect(jsonPath("$.erros.nome").value("Nome obrigatorio"))
                .andExpect(jsonPath("$.erros.email").value("Email invalido"))
                .andExpect(jsonPath("$.erros.cpf").value("CPF obrigatorio"));
    }

    @Test
    void criar_quandoCorpoMalformado_deveRetornarBadRequest() throws Exception {
        String payload = """
                {
                  "nome": "Maria",
                  "dataNascimento": "1990-04-15",
                  "cpf": "529.982.247-25"
                }
                """;

        mockMvc.perform(post("/api/v1/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Corpo da requisicao invalido ou malformado"));
    }

    @Test
    void atualizar_quandoRequestValido_deveRetornarOk() throws Exception {
        PessoaResponseDTO response = PessoaResponseDTO.builder()
                .id(1L)
                .nome("Ana")
                .sexo(Sexo.M)
                .email("ana@email.com")
                .dataNascimento(LocalDate.of(1988, 7, 10))
                .estado("PR")
                .pais("Brasil")
                .cpf("11144477735")
                .build();

        when(pessoaService.atualizar(any(Long.class), any(PessoaRequestDTO.class))).thenReturn(response);

        String payload = """
                {
                  "nome": "Ana",
                  "sexo": "M",
                  "email": "ana@email.com",
                  "dataNascimento": "10/07/1988",
                  "estado": "PR",
                  "pais": "Brasil",
                  "cpf": "111.444.777-35"
                }
                """;

        mockMvc.perform(put("/api/v1/pessoas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Ana"));
    }

    @Test
    void buscaPessoaPorId_quandoEncontrada_deveRetornarOk() throws Exception {
        PessoaResponseDTO response = PessoaResponseDTO.builder()
                .id(10L)
                .nome("Carlos")
                .dataNascimento(LocalDate.of(1995, 1, 20))
                .cpf("12345678909")
                .build();

        when(pessoaService.buscaPessoaPorId(10L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/pessoas/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.nome").value("Carlos"));
    }

    @Test
    void buscaPessoaPorId_quandoNaoEncontrada_deveRetornarNotFound() throws Exception {
        when(pessoaService.buscaPessoaPorId(99L)).thenThrow(new BusinessNotFoundException("Pessoa nao encontrada."));

        mockMvc.perform(get("/api/v1/pessoas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Pessoa nao encontrada."))
                .andExpect(jsonPath("$.caminho").value("/api/v1/pessoas/99"));
    }

    @Test
    void listarPessoas_deveRetornarLista() throws Exception {
        List<PessoaResponseDTO> pessoas = List.of(
                PessoaResponseDTO.builder().id(1L).nome("Maria").build(),
                PessoaResponseDTO.builder().id(2L).nome("Joao").build()
        );

        when(pessoaService.listarPessoas()).thenReturn(pessoas);

        mockMvc.perform(get("/api/v1/pessoas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].nome").value("Joao"));
    }
}
