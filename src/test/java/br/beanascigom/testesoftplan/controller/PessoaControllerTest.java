package br.beanascigom.testesoftplan.controller;

import br.beanascigom.testesoftplan.builder.PessoaBuilder;
import br.beanascigom.testesoftplan.dto.PessoaRequestDTO;
import br.beanascigom.testesoftplan.dto.PessoaResponseDTO;
import br.beanascigom.testesoftplan.exception.BusinessNotFoundException;
import br.beanascigom.testesoftplan.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("dev")
@TestPropertySource(properties = {"app.security.basic.username=teste", "app.security.basic.password=teste123"})
class PessoaControllerTest {

    private static final String USERNAME = "teste";
    private static final String PASSWORD = "teste123";
    private static final String apiUrl = "/pessoas/v1";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PessoaService pessoaService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void criar_quandoRequestValido_deveRetornarCreated() throws Exception {
        when(pessoaService.criar(any(PessoaRequestDTO.class))).thenReturn(PessoaBuilder.getPessoaBuilder());

        mockMvc.perform(post(apiUrl + "/")
                        .with(usuarioAutenticado())
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
        mockMvc.perform(post(apiUrl + "/")
                        .with(usuarioAutenticado())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadSemCampoObrigatorio))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Falha de validacao no corpo da requisicao"))
                .andExpect(jsonPath("$.erros.nome").value("Nome obrigatorio"))
                .andExpect(jsonPath("$.erros.email").value("Email invalido"))
                .andExpect(jsonPath("$.erros.cpf").value("CPF obrigatorio"));
    }

    @Test
    void criar_quandoCorpoMalformado_deveRetornarBadRequest() throws Exception {
        mockMvc.perform(post(apiUrl + "/")
                        .with(usuarioAutenticado())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadMalFormado))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Corpo da requisicao invalido ou malformado"));
    }

    @Test
    void atualizar_quandoRequestValido_deveRetornarOk() throws Exception {
        when(pessoaService.atualizar(any(Long.class), any(PessoaRequestDTO.class))).thenReturn(PessoaBuilder.getPessoaBuilder());

        mockMvc.perform(put(apiUrl + "/{id}", 1)
                        .with(usuarioAutenticado())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    void buscaPessoaPorId_quandoEncontrada_deveRetornarOk() throws Exception {
        when(pessoaService.buscaPessoaPorId(1L)).thenReturn(PessoaBuilder.getPessoaBuilder());

        mockMvc.perform(get(apiUrl + "/{id}", 1L)
                        .with(usuarioAutenticado()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    void buscaPessoaPorId_quandoNaoEncontrada_deveRetornarNotFound() throws Exception {
        when(pessoaService.buscaPessoaPorId(99L)).thenThrow(new BusinessNotFoundException("Pessoa nao encontrada."));

        mockMvc.perform(get(apiUrl + "/99", 99L)
                        .with(usuarioAutenticado()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Pessoa nao encontrada."))
                .andExpect(jsonPath("$.caminho").value(apiUrl + "/99"));
    }

    @Test
    void listarPessoas_deveRetornarLista() throws Exception {
        List<PessoaResponseDTO> pessoas = List.of(
                PessoaResponseDTO.builder().id(1L).nome("Maria").build(),
                PessoaResponseDTO.builder().id(2L).nome("Joao").build()
        );

        when(pessoaService.listarPessoas()).thenReturn(pessoas);

        mockMvc.perform(get(apiUrl + "/")
                        .with(usuarioAutenticado()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].nome").value("Joao"));
    }

    @Test
    void listarPessoas_noPathCompativelApiV1_deveRetornarLista() throws Exception {
        List<PessoaResponseDTO> pessoas = List.of(
                PessoaResponseDTO.builder().id(1L).nome("Maria").build()
        );

        when(pessoaService.listarPessoas()).thenReturn(pessoas);

        mockMvc.perform(get("/api/v1/pessoas/")
                        .with(usuarioAutenticado()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Maria"));
    }

    @Test
    void criar_quandoNaoAutenticado_deveRetornarUnauthorized() throws Exception {
        mockMvc.perform(post(apiUrl + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void source_quandoSemAutenticacao_deveRetornarOk() throws Exception {
        mockMvc.perform(get("/source"))
                .andExpect(status().isOk());
    }

    private RequestPostProcessor usuarioAutenticado() {
        return httpBasic(USERNAME, PASSWORD);
    }

    @TestConfiguration
    static class MockConfig {
        @Bean
        @Primary
        PessoaService pessoaService() {
            return mock(PessoaService.class);
        }
    }

    private static final String payload =
        """
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

    private static final String payloadMalFormado =
        """
            {
              "nome": "Maria",
              "dataNascimento": "1990-04-15",
              "cpf": "529.982.247-25"
            }
        """;

    private static final String payloadSemCampoObrigatorio =
        """
            {
              "email": "email-invalido",
              "dataNascimento": "15/04/2999",
              "cpf": ""
            }
        """;
}
