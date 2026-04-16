package br.beanascigom.testesoftplan.controller;

import br.beanascigom.testesoftplan.builder.PessoaBuilder;
import br.beanascigom.testesoftplan.dto.PessoaRequestV2DTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("dev")
@TestPropertySource(properties = {"app.security.basic.username=teste", "app.security.basic.password=teste123"})
class PessoaV2ControllerTest {

    private static final String USERNAME = "teste";
    private static final String PASSWORD = "teste123";
    private static final String API_URL = "/api/v2/pessoas";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PessoaService pessoaService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void criar_quandoRequestValido_deveRetornarCreated() throws Exception {
        when(pessoaService.criar(any(PessoaRequestV2DTO.class))).thenReturn(PessoaBuilder.getPessoaBuilder());

        mockMvc.perform(post(API_URL + "/")
                        .with(usuarioAutenticado())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadValido))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria"));

        ArgumentCaptor<PessoaRequestV2DTO> captor = ArgumentCaptor.forClass(PessoaRequestV2DTO.class);
        verify(pessoaService).criar(captor.capture());
        assertEquals("Florianopolis", captor.getValue().getEndereco().getCidade());
    }

    @Test
    void criar_quandoSemEndereco_deveRetornarBadRequest() throws Exception {
        mockMvc.perform(post(API_URL + "/")
                        .with(usuarioAutenticado())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadSemEndereco))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Falha de validacao no corpo da requisicao"))
                .andExpect(jsonPath("$.erros.endereco").value("Endereco obrigatorio"));
    }

    @Test
    void buscaPessoaPorId_quandoAutenticado_deveRetornarOk() throws Exception {
        when(pessoaService.buscaPessoaPorId(1L)).thenReturn(PessoaBuilder.getPessoaBuilder());

        mockMvc.perform(get(API_URL + "/{id}", 1L)
                        .with(usuarioAutenticado()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void criar_quandoNaoAutenticado_deveRetornarUnauthorized() throws Exception {
        mockMvc.perform(post(API_URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadValido))
                .andExpect(status().isUnauthorized());
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

    private static final String payloadValido =
            """
                {
                  "nome": "Maria",
                  "sexo": "M",
                  "email": "maria@email.com",
                  "dataNascimento": "15/04/1990",
                  "estado": "SC",
                  "pais": "Brasil",
                  "cpf": "529.982.247-25",
                  "endereco": {
                    "cidade": "Florianopolis",
                    "rua": "Rua das Flores",
                    "numero": 100,
                    "cep": "88000-000",
                    "bairro": "Centro",
                    "complemento": "Apto 12"
                  }
                }
            """;

    private static final String payloadSemEndereco =
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
}
