package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.fachada.ProfissionalFachada;
import com.mxs.backend.modelo.ContatoModelo;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ContatoRepositorio;
import com.mxs.backend.requisicao.ContatoControladorObterRequisicao;
import com.mxs.backend.resposta.ContatoControladorObterResposta;
import com.mxs.backend.tipo.CargoTipo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContatoObterFuncaoTestes {

    @InjectMocks
    private ContatoObterFuncao contatoObterFuncao;
    @Mock
    private ProfissionalFachada profissionalFachada;
    @Mock
    private ContatoConversor contatoConversor;
    @Mock
    private ContatoRepositorio contatoRepositorio;

    @DisplayName("Teste de busca com sucesso.")
    @Test
    void testarBuscaComSucesso() {

        var codigo = UUID.randomUUID().toString();
        var nome = "Paulo";
        var contato = "(11) 9 9999-9999";
        var contatoProfissional = UUID.randomUUID().toString();
        var nascimento = LocalDate.now();
        var cargo = CargoTipo.DESENVOLVEDOR;

        ContatoControladorObterRequisicao contatoControladorObterRequisicao =
                ContatoControladorObterRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nome)
                        .contato(contato)
                        .codigoProfissional(contatoProfissional)
                        .build();

        ProfissionalModelo profissionalModelo =
                ProfissionalModelo
                        .builder()
                        .nome(nome)
                        .nascimento(nascimento)
                        .cargoTipo(cargo)
                        .build();

        ContatoModelo contatoModelo =
                ContatoModelo
                        .builder()
                        .nome(nome)
                        .contato(contato)
                        .profissionalModelo(profissionalModelo)
                        .build();

        ContatoControladorObterResposta contatoControladorObterResposta =
                ContatoControladorObterResposta
                        .builder()
                        .codigo(contato)
                        .nome(nome)
                        .contato(contato)
                        .codigoProfissional(contatoProfissional)
                        .build();

        var contatoModeloLista = Collections.singletonList(contatoModelo);
        var esperado = Collections.singletonList(contatoControladorObterResposta);

        when(this.contatoRepositorio.findAll(Mockito.any(Specification.class))).thenReturn(contatoModeloLista);
        when(this.contatoConversor.modeloListaParaControladorObterRespostaLista(contatoModeloLista)).thenReturn(esperado);

        var atual = this.contatoObterFuncao.obterContatos(contatoControladorObterRequisicao);

        assertEquals(esperado, atual);
    }

    @DisplayName("Teste Contato não encontrado.")
    @Test
    void testarContatoNaoEncontrado() {

        var codigo = UUID.randomUUID().toString();
        var nome = "Paulo";
        var contato = "(11) 9 9999-9999";
        var contatoProfissional = UUID.randomUUID().toString();

        ContatoControladorObterRequisicao contatoControladorObterRequisicao =
                ContatoControladorObterRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nome)
                        .contato(contato)
                        .codigoProfissional(contatoProfissional)
                        .build();

        when(this.contatoRepositorio.findAll(Mockito.any(Specification.class))).thenReturn(Collections.EMPTY_LIST);

        NaoEncontradoExcecao naoEncontradoExcecao =
                assertThrows(NaoEncontradoExcecao.class, () ->
                        this.contatoObterFuncao.obterContatos(contatoControladorObterRequisicao));

        verify(this.contatoRepositorio, times(1)).findAll(Mockito.any(Specification.class));

        assertTrue(naoEncontradoExcecao.getMessage().contains("Contato não encontrado"));
    }
}
