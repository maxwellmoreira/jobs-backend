package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.fachada.ProfissionalFachada;
import com.mxs.backend.modelo.ContatoModelo;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ContatoRepositorio;
import com.mxs.backend.requisicao.ContatoControladorAtualizarRequisicao;
import com.mxs.backend.tipo.CargoTipo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContatoAtualizarFuncaoTestes {

    @InjectMocks
    private ContatoAtualizarFuncao contatoAtualizarFuncao;
    @Mock
    private ProfissionalFachada profissionalFachada;
    @Mock
    private ContatoConversor contatoConversor;
    @Mock
    private ContatoRepositorio contatoRepositorio;

    @DisplayName("Teste de atualização com sucesso.")
    @Test
    void testarAtualizacaoComSucesso() {

        var codigo = UUID.randomUUID().toString();
        var nome = "Paulo";
        var nomeCompleto = "Paulo de Tarso";
        var contato = "(11) 9 9999-9999";
        var contatoProfissional = UUID.randomUUID().toString();
        var nascimento = LocalDate.now();
        var cargo = CargoTipo.DESENVOLVEDOR;

        ContatoControladorAtualizarRequisicao contatoControladorAtualizarRequisicao =
                ContatoControladorAtualizarRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nomeCompleto)
                        .contato(contato)
                        .codigoProfissional(contatoProfissional)
                        .build();

        ProfissionalModelo profissionalEncontradoNoBanco =
                ProfissionalModelo
                        .builder()
                        .nome(nome)
                        .nascimento(nascimento)
                        .cargoTipo(cargo)
                        .build();

        ContatoModelo contatoEncontradoNoBanco =
                ContatoModelo
                        .builder()
                        .nome(nome)
                        .contato(contato)
                        .profissionalModelo(profissionalEncontradoNoBanco)
                        .build();

        ContatoModelo contatoAtualizado =
                ContatoModelo
                        .builder()
                        .nome(nomeCompleto)
                        .contato(contato)
                        .profissionalModelo(profissionalEncontradoNoBanco)
                        .build();

        when(this.contatoRepositorio.findByCodigoEquals(contatoControladorAtualizarRequisicao.codigo()))
                .thenReturn(Optional.of(contatoEncontradoNoBanco));
        when(this.profissionalFachada.obterProfissionalPorCodigo(contatoControladorAtualizarRequisicao.codigoProfissional()))
                .thenReturn(profissionalEncontradoNoBanco);
        when(this.contatoConversor.controladorAtualizarRequisicaoParaModelo(
                contatoControladorAtualizarRequisicao, contatoEncontradoNoBanco, profissionalEncontradoNoBanco))
                .thenReturn(contatoAtualizado);
        when(this.contatoRepositorio.save(any(ContatoModelo.class))).thenReturn(contatoAtualizado);

        this.contatoAtualizarFuncao.atualizarContato(contatoControladorAtualizarRequisicao);

        verify(this.contatoRepositorio, times(1))
                .findByCodigoEquals(contatoControladorAtualizarRequisicao.codigo());
        verify(this.profissionalFachada, times(1))
                .obterProfissionalPorCodigo(contatoControladorAtualizarRequisicao.codigoProfissional());
        verify(this.contatoConversor, times(1))
                .controladorAtualizarRequisicaoParaModelo(contatoControladorAtualizarRequisicao, contatoEncontradoNoBanco, profissionalEncontradoNoBanco);
        verify(this.contatoRepositorio, times(1))
                .save(contatoAtualizado);
    }

    @DisplayName("Teste de Contato não encontrado.")
    @Test
    void testarContatoNaoEncontrado() {

        var codigo = UUID.randomUUID().toString();
        var nomeCompleto = "Paulo de Tarso";
        var contato = "(11) 9 9999-9999";
        var contatoProfissional = UUID.randomUUID().toString();

        ContatoControladorAtualizarRequisicao contatoControladorAtualizarRequisicao =
                ContatoControladorAtualizarRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nomeCompleto)
                        .contato(contato)
                        .codigoProfissional(contatoProfissional)
                        .build();

        when(this.contatoRepositorio.findByCodigoEquals(contatoControladorAtualizarRequisicao.codigo()))
                .thenReturn(Optional.empty());

        NaoEncontradoExcecao naoEncontradoExcecao =
                assertThrows(NaoEncontradoExcecao.class, () ->
                        this.contatoAtualizarFuncao.atualizarContato(contatoControladorAtualizarRequisicao));

        verify(this.contatoRepositorio, times(1))
                .findByCodigoEquals(contatoControladorAtualizarRequisicao.codigo());

        assertTrue(naoEncontradoExcecao.getMessage().contains("Contato não encontrado"));
    }
}
