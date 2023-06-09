package com.mxs.backend.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.backend.fachada.ProfissionalFachada;
import com.mxs.backend.requisicao.ProfissionalControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorCriarRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorExcluirRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorObterRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorAtualizarResposta;
import com.mxs.backend.resposta.ProfissionalControladorCriarResposta;
import com.mxs.backend.resposta.ProfissionalControladorExcluirResposta;
import com.mxs.backend.resposta.ProfissionalControladorObterResposta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfissionalControladorTestes {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfissionalFachada profissionalFachada;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testar criar profissional ok")
    public void testCriarProfissional() throws Exception {

        String codigo = UUID.randomUUID().toString();

        ProfissionalControladorCriarRequisicao requisicao = ProfissionalControladorCriarRequisicao.builder()
                .nome("Francisco")
                .cargo("Desenvolvedor")
                .nascimento("1990-01-01")
                .build();

        ProfissionalControladorCriarResposta respostaEsperada = ProfissionalControladorCriarResposta.builder()
                .codigo(codigo)
                .build();

        when(profissionalFachada.criarProfissional(any(ProfissionalControladorCriarRequisicao.class)))
                .thenReturn(respostaEsperada);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/profissionais/profissional")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Francisco\", \"cargo\": \"Desenvolvedor\", \"nascimento\": \"1990-01-01\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(codigo));
    }

    @Test
    @DisplayName("Testar obter profissional ok")
    public void testObterProfissional() throws Exception {

        String codigo = UUID.randomUUID().toString();

        ProfissionalControladorObterResposta profissionalControladorObterResposta =
                new ProfissionalControladorObterResposta(codigo, "João", "Desenvolvedor", "1990-01-01", Collections.EMPTY_LIST);

        List<ProfissionalControladorObterResposta> profissionalControladorObterRespostas = new ArrayList<>();
        profissionalControladorObterRespostas.add(profissionalControladorObterResposta);

        ProfissionalControladorObterRequisicao profissionalControladorObterRequisicao = ProfissionalControladorObterRequisicao.builder()
                .codigo(codigo)
                .nome("João")
                .nascimento("1990-01-01")
                .cargo("Desenvolvedor")
                .build();

        when(profissionalFachada.obterProfissionais(profissionalControladorObterRequisicao)).thenReturn(profissionalControladorObterRespostas);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profissionais/buscar-por-filtro")
                        .param("codigo", codigo)
                        .param("nome", "João")
                        .param("nascimento", "1990-01-01")
                        .param("cargo", "Desenvolvedor")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(profissionalControladorObterRespostas)));
    }

    @Test
    @DisplayName("Testar atualizar profissional ok")
    public void testAtualizarProfissional() throws Exception {

        String codigo = UUID.randomUUID().toString();

        ProfissionalControladorAtualizarRequisicao profissionalControladorAtualizarRequisicao = ProfissionalControladorAtualizarRequisicao.builder()
                .codigo(codigo)
                .nome("João")
                .cargo("Gerente")
                .nascimento("1990-01-01")
                .build();

        ProfissionalControladorAtualizarResposta profissionalControladorObterResposta =
                ProfissionalControladorAtualizarResposta
                        .builder()
                        .codigo(codigo)
                        .build();

        when(profissionalFachada.atualizarProfissional(profissionalControladorAtualizarRequisicao)).thenReturn(profissionalControladorObterResposta);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/profissionais/profissional/{codigo}", codigo)
                        .content(objectMapper.writeValueAsString(profissionalControladorAtualizarRequisicao))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(codigo));
    }

    @Test
    @DisplayName("Testar excluir profissional ok")
    public void testExcluirProfissional() throws Exception {

        String codigo = UUID.randomUUID().toString();

        ProfissionalControladorExcluirRequisicao profissionalControladorExcluirRequisicao = ProfissionalControladorExcluirRequisicao.builder()
                .codigo(codigo)
                .build();

        ProfissionalControladorExcluirResposta profissionalControladorExcluirResposta =
                ProfissionalControladorExcluirResposta
                        .builder()
                        .codigo(codigo)
                        .build();

        when(profissionalFachada.excluirProfissional(profissionalControladorExcluirRequisicao)).thenReturn(profissionalControladorExcluirResposta);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/profissionais/profissional/{codigo}", codigo)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(codigo));
    }
}
