package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ProfissionalConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorAtualizarRequisicao;
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
public class ProfissionalAtualizarFuncaoTestes {

    @InjectMocks
    private ProfissionalAtualizarFuncao profissionalAtualizarFuncao;
    @Mock
    private ProfissionalConversor profissionalConversor;
    @Mock
    private ProfissionalRepositorio profissionalRepositorio;

    @DisplayName("Teste de alteração com sucesso.")
    @Test
    void testarAlteracaoComSucesso() {

        var codigo = UUID.randomUUID().toString();
        var nome = "Maria";
        var nomeAtualizado = "Maria Pereira da Silva";
        var cargo = CargoTipo.DESENVOLVEDOR;
        var nascimento = LocalDate.now();

        ProfissionalControladorAtualizarRequisicao profissionalControladorAtualizarRequisicao =
                ProfissionalControladorAtualizarRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nomeAtualizado)
                        .cargo(cargo.name())
                        .nascimento(nascimento.toString())
                        .build();

        ProfissionalModelo profissionalEncontradoNoBanco =
                ProfissionalModelo
                        .builder()
                        .nome(nome)
                        .cargoTipo(cargo)
                        .nascimento(nascimento)
                        .build();

        ProfissionalModelo profissionalAtualizado =
                ProfissionalModelo
                        .builder()
                        .nome(nomeAtualizado)
                        .cargoTipo(cargo)
                        .nascimento(nascimento)
                        .build();

        when(this.profissionalRepositorio.findByCodigoEquals(profissionalControladorAtualizarRequisicao.codigo()))
                .thenReturn(Optional.of(profissionalEncontradoNoBanco));
        when(this.profissionalConversor.controladorAtualizarRequisicaoEModeloParaModelo(
                profissionalControladorAtualizarRequisicao, profissionalEncontradoNoBanco)).thenReturn(profissionalAtualizado);
        when(this.profissionalRepositorio.save(any(ProfissionalModelo.class)))
                .thenReturn(profissionalAtualizado);

        this.profissionalAtualizarFuncao.atualizarProfissional(profissionalControladorAtualizarRequisicao);

        verify(this.profissionalRepositorio, times(1))
                .findByCodigoEquals(profissionalControladorAtualizarRequisicao.codigo());
        verify(this.profissionalConversor, times(1))
                .controladorAtualizarRequisicaoEModeloParaModelo(profissionalControladorAtualizarRequisicao, profissionalEncontradoNoBanco);
        verify(this.profissionalRepositorio, times(1))
                .save(profissionalAtualizado);
    }

    @DisplayName("Teste de Profissional não encontrado.")
    @Test
    void testarProfissionalNaoEncontrado() {

        var codigo = UUID.randomUUID().toString();
        var nome = "Maria";
        var nomeAtualizado = "Maria Pereira da Silva";
        var cargo = CargoTipo.DESENVOLVEDOR;
        var nascimento = LocalDate.now();

        ProfissionalControladorAtualizarRequisicao profissionalControladorAtualizarRequisicao =
                ProfissionalControladorAtualizarRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nomeAtualizado)
                        .cargo(cargo.name())
                        .nascimento(nascimento.toString())
                        .build();

        when(this.profissionalRepositorio.findByCodigoEquals(profissionalControladorAtualizarRequisicao.codigo())).thenReturn(Optional.empty());

        NaoEncontradoExcecao naoEncontradoExcecao =
                assertThrows(NaoEncontradoExcecao.class, () ->
                        this.profissionalAtualizarFuncao.atualizarProfissional(profissionalControladorAtualizarRequisicao));

        verify(this.profissionalRepositorio, times(1))
                .findByCodigoEquals(profissionalControladorAtualizarRequisicao.codigo());

        assertTrue(naoEncontradoExcecao.getMessage().contains("Profissional não encontrado"));
    }
}
