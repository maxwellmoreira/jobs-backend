package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.modelo.ContatoModelo;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ContatoRepositorio;
import com.mxs.backend.requisicao.ContatoControladorExcluirRequisicao;
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
public class ContatoExcluirFuncaoTestes {

    @InjectMocks
    private ContatoExcluirFuncao contatoExcluirFuncao;
    @Mock
    private ContatoConversor contatoConversor;
    @Mock
    private ContatoRepositorio contatoRepositorio;

    @DisplayName("Teste de exclusão com sucesso.")
    @Test
    void testarExclusaoComSucesso() {

        var codigo = UUID.randomUUID().toString();
        var nome = "Paulo";
        var nascimento = LocalDate.now();
        var cargo = CargoTipo.DESENVOLVEDOR;
        var contato = "(11) 9 9999-9999";

        ContatoControladorExcluirRequisicao contatoControladorExcluirRequisicao =
                ContatoControladorExcluirRequisicao
                        .builder()
                        .codigo(codigo)
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

        when(this.contatoRepositorio.findByCodigoEquals(contatoControladorExcluirRequisicao.codigo()))
                .thenReturn(Optional.of(contatoEncontradoNoBanco));
        when(this.contatoRepositorio.save(any(ContatoModelo.class)))
                .thenReturn(contatoEncontradoNoBanco);

        this.contatoExcluirFuncao.excluirContato(contatoControladorExcluirRequisicao);

        verify(this.contatoRepositorio, times(1))
                .findByCodigoEquals(contatoControladorExcluirRequisicao.codigo());
        verify(this.contatoRepositorio, times(1))
                .save(contatoEncontradoNoBanco);
    }

    @DisplayName("Teste de Contato não encontrado.")
    @Test
    void testarContatoNaoEncontrado() {

        var codigo = UUID.randomUUID().toString();

        ContatoControladorExcluirRequisicao contatoControladorExcluirRequisicao =
                ContatoControladorExcluirRequisicao
                        .builder()
                        .codigo(codigo)
                        .build();

        when(this.contatoRepositorio.findByCodigoEquals(contatoControladorExcluirRequisicao.codigo()))
                .thenReturn(Optional.empty());

        NaoEncontradoExcecao naoEncontradoExcecao =
                assertThrows(NaoEncontradoExcecao.class, () ->
                        this.contatoExcluirFuncao.excluirContato(contatoControladorExcluirRequisicao));

        verify(this.contatoRepositorio, times(1))
                .findByCodigoEquals(contatoControladorExcluirRequisicao.codigo());

        assertTrue(naoEncontradoExcecao.getMessage().contains("Contato não encontrado"));
    }
}
