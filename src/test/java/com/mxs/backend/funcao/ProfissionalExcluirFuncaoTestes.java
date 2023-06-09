package com.mxs.backend.funcao;

import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorExcluirRequisicao;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfissionalExcluirFuncaoTestes {

    @InjectMocks
    private ProfissionalExcluirFuncao profissionalExcluirFuncao;
    @Mock
    private ProfissionalRepositorio profissionalRepositorio;

    @DisplayName("Teste de exclusão com sucesso.")
    @Test
    void testarExclusaoComSucesso() {

        var codigo = UUID.randomUUID().toString();
        var nome = "Pedro";
        var cargo = CargoTipo.DESENVOLVEDOR;
        var nascimento = LocalDate.now();

        ProfissionalControladorExcluirRequisicao profissionalControladorExcluirRequisicao =
                ProfissionalControladorExcluirRequisicao
                        .builder()
                        .codigo(codigo)
                        .build();

        ProfissionalModelo profissionalEncontradoNoBanco =
                ProfissionalModelo
                        .builder()
                        .nome(nome)
                        .cargoTipo(cargo)
                        .nascimento(nascimento)
                        .build();

        when(this.profissionalRepositorio.findByCodigoEquals(profissionalControladorExcluirRequisicao.codigo()))
                .thenReturn(Optional.of(profissionalEncontradoNoBanco));
        when(this.profissionalRepositorio.save(profissionalEncontradoNoBanco))
                .thenReturn(profissionalEncontradoNoBanco);

        this.profissionalExcluirFuncao.excluirProfissional(profissionalControladorExcluirRequisicao);

        verify(this.profissionalRepositorio, times(1))
                .findByCodigoEquals(profissionalControladorExcluirRequisicao.codigo());
        verify(this.profissionalRepositorio, times(1))
                .save(profissionalEncontradoNoBanco);
    }

    @DisplayName("Teste Profissional não encontrado.")
    @Test
    void testarProfissionalNaoEncontrado() {

        var codigo = UUID.randomUUID().toString();

        ProfissionalControladorExcluirRequisicao profissionalControladorExcluirRequisicao =
                ProfissionalControladorExcluirRequisicao
                        .builder()
                        .codigo(codigo)
                        .build();

        when(this.profissionalRepositorio.findByCodigoEquals(profissionalControladorExcluirRequisicao.codigo())).thenReturn(Optional.empty());

        NaoEncontradoExcecao naoEncontradoExcecao =
                assertThrows(NaoEncontradoExcecao.class, () ->
                        this.profissionalExcluirFuncao.excluirProfissional(profissionalControladorExcluirRequisicao));

        verify(this.profissionalRepositorio, times(1))
                .findByCodigoEquals(profissionalControladorExcluirRequisicao.codigo());

        assertTrue(naoEncontradoExcecao.getMessage().contains("Profissional não encontrado"));
    }
}
