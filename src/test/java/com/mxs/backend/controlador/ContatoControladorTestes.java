package com.mxs.backend.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxs.backend.fachada.ContatoFachada;
import com.mxs.backend.requisicao.ContatoControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ContatoControladorCriarRequisicao;
import com.mxs.backend.requisicao.ContatoControladorExcluirRequisicao;
import com.mxs.backend.requisicao.ContatoControladorObterRequisicao;
import com.mxs.backend.resposta.ContatoControladorAtualizarResposta;
import com.mxs.backend.resposta.ContatoControladorCriarResposta;
import com.mxs.backend.resposta.ContatoControladorExcluirResposta;
import com.mxs.backend.resposta.ContatoControladorObterResposta;
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
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ContatoControladorTestes {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContatoFachada contatoFachada;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Testar criar contato ok")
    void testarCriarContatoOk() throws Exception {

        String nome = "João";
        String contato = "joao@example.com";
        String codigoProfissional = "123";

        ContatoControladorCriarRequisicao contatoControladorCriarRequisicao =
                ContatoControladorCriarRequisicao
                        .builder()
                        .nome(nome)
                        .contato(contato)
                        .codigoProfissional(codigoProfissional)
                        .build();

        ContatoControladorCriarResposta contatoControladorCriarResposta =
                ContatoControladorCriarResposta
                        .builder()
                        .codigo("456")
                        .build();

        when(contatoFachada.criarContato(contatoControladorCriarRequisicao)).thenReturn(contatoControladorCriarResposta);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/contatos/contato")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contatoControladorCriarRequisicao)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value("456"));
    }

    @Test
    @DisplayName("Testar obter contato ok")
    void testarObterContatoOk() throws Exception {

        String codigo = "123";
        String nome = "João";
        String contato = "joao@example.com";
        String codigoProfissional = "456";

        ContatoControladorObterRequisicao contatoControladorObterRequisicao = ContatoControladorObterRequisicao.builder()
                .codigo(codigo)
                .nome(nome)
                .contato(contato)
                .codigoProfissional(codigoProfissional)
                .build();

        ContatoControladorObterResposta contatoControladorObterResposta = ContatoControladorObterResposta.builder()
                .codigo("789")
                .nome("Maria")
                .contato("maria@example.com")
                .codigoProfissional("456")
                .build();

        List<ContatoControladorObterResposta> contatoControladorObterRespostaLista = new ArrayList<>();
        contatoControladorObterRespostaLista.add(contatoControladorObterResposta);

        when(contatoFachada.obterContatos(contatoControladorObterRequisicao)).thenReturn(contatoControladorObterRespostaLista);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contatos/buscar-por-filtro")
                        .param("codigo", codigo)
                        .param("nome", nome)
                        .param("contato", contato)
                        .param("codigoProfissional", codigoProfissional))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codigo").value("789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Maria"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].contato").value("maria@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codigoProfissional").value("456"));
    }

    @Test
    @DisplayName("Testar atualizar contato ok")
    void testarAtualizarContatoOk() throws Exception {

        String codigo = "123";
        String nome = "João";
        String contato = "joao@example.com";
        String codigoProfissional = "456";

        ContatoControladorAtualizarRequisicao contatoControladorAtualizarRequisicao =
                ContatoControladorAtualizarRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nome)
                        .contato(contato)
                        .codigoProfissional(codigoProfissional)
                        .build();

        ContatoControladorAtualizarResposta contatoControladorAtualizarResposta =
                ContatoControladorAtualizarResposta
                        .builder()
                        .codigo("789")
                        .build();

        when(contatoFachada.atualizarContato(contatoControladorAtualizarRequisicao)).thenReturn(contatoControladorAtualizarResposta);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/contatos/contato/{codigo}", codigo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contatoControladorAtualizarRequisicao)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value("789"));
    }

    @Test
    @DisplayName("Testar excluir contato ok")
    void testarExcluirContatoOk() throws Exception {

        String codigo = "123";

        ContatoControladorExcluirRequisicao contatoControladorExcluirRequisicao =
                ContatoControladorExcluirRequisicao
                        .builder()
                        .codigo(codigo)
                        .build();

        ContatoControladorExcluirResposta contatoControladorExcluirResposta =
                ContatoControladorExcluirResposta
                        .builder()
                        .codigo(codigo)
                        .build();

        when(contatoFachada.excluirContato(contatoControladorExcluirRequisicao)).thenReturn(contatoControladorExcluirResposta);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/contatos/contato/{codigo}", codigo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contatoControladorExcluirRequisicao)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(codigo));
    }
}
