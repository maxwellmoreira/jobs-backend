package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ProfissionalConversor;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorCriarRequisicao;
import com.mxs.backend.tipo.CargoTipo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProfissionalIncluirFuncaoTestes {

    @InjectMocks
    private ProfissionalIncluirFuncao profissionalIncluirFuncao;
    @Mock
    private ProfissionalConversor profissionalConversor;
    @Mock
    private ProfissionalRepositorio profissionalRepositorio;

    @DisplayName("Teste de inclusão com sucesso.")
    @Test
    void testarInclusaoComSucesso() {

        var cargo = CargoTipo.DESENVOLVEDOR;
        var nome = "Maria";
        var nascimento = LocalDate.now();

        ProfissionalControladorCriarRequisicao profissionalControladorCriarRequisicao =
                ProfissionalControladorCriarRequisicao
                        .builder()
                        .cargo(cargo.name())
                        .nome(nome)
                        .nascimento(nascimento.toString())
                        .build();

        ProfissionalModelo profissionalModelo =
                ProfissionalModelo
                        .builder()
                        .cargoTipo(cargo)
                        .nome(nome)
                        .nascimento(nascimento)
                        .build();

        when(this.profissionalConversor.controladorCriarRequisicaoParaModelo(profissionalControladorCriarRequisicao)).thenReturn(profissionalModelo);
        when(this.profissionalRepositorio.save(any(ProfissionalModelo.class))).thenReturn(profissionalModelo);

        this.profissionalIncluirFuncao.criarProfissional(profissionalControladorCriarRequisicao);

        verify(this.profissionalConversor, times(1)).controladorCriarRequisicaoParaModelo(profissionalControladorCriarRequisicao);
        verify(this.profissionalRepositorio, times(1)).save(profissionalModelo);
    }
}
