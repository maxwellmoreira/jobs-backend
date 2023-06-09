package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ProfissionalConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorObterRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorObterResposta;
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
public class ProfissionalObterFuncaoTestes {

    @InjectMocks
    private ProfissionalObterFuncao profissionalObterFuncao;
    @Mock
    private ProfissionalConversor profissionalConversor;
    @Mock
    private ProfissionalRepositorio profissionalRepositorio;

    @DisplayName("Teste de busca com sucesso.")
    @Test
    void testarBuscaComSucesso() {

        var codigo = UUID.randomUUID().toString();
        var nome = "João";
        var nascimento = "2023-06-08";
        var cargo = CargoTipo.DESENVOLVEDOR;

        ProfissionalControladorObterRequisicao profissionalControladorObterRequisicao =
                ProfissionalControladorObterRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nome)
                        .nascimento(nascimento)
                        .cargo(cargo.name())
                        .build();

        ProfissionalModelo profissionalModelo =
                ProfissionalModelo
                        .builder()
                        .nome(nome)
                        .nascimento(LocalDate.parse(nascimento))
                        .cargoTipo(cargo)
                        .build();

        ProfissionalControladorObterResposta profissionalControladorObterResposta =
                ProfissionalControladorObterResposta
                        .builder()
                        .codigo(codigo)
                        .nome(nome)
                        .nascimento(nascimento)
                        .cargo(cargo.name())
                        .build();

        var profissionalModeloLista = Collections.singletonList(profissionalModelo);
        var esperado = Collections.singletonList(profissionalControladorObterResposta);

        when(this.profissionalRepositorio.findAll(Mockito.any(Specification.class))).thenReturn(profissionalModeloLista);
        when(this.profissionalConversor.modeloListaParaControladorObterRespostaLista(profissionalModeloLista)).thenReturn(esperado);

        var atual = this.profissionalObterFuncao.obterProfissionais(profissionalControladorObterRequisicao);

        assertEquals(esperado, atual);
    }

    @DisplayName("Teste Profissional não encontrado.")
    @Test
    void testarProfissionalNaoEncontrado() {

        var codigo = UUID.randomUUID().toString();
        var nome = "João";
        var nascimento = "2023-06-08";
        var cargo = CargoTipo.DESENVOLVEDOR;

        ProfissionalControladorObterRequisicao profissionalControladorObterRequisicao =
                ProfissionalControladorObterRequisicao
                        .builder()
                        .codigo(codigo)
                        .nome(nome)
                        .nascimento(nascimento)
                        .cargo(cargo.name())
                        .build();

        when(this.profissionalRepositorio.findAll(Mockito.any(Specification.class))).thenReturn(Collections.EMPTY_LIST);

        NaoEncontradoExcecao naoEncontradoExcecao =
                assertThrows(NaoEncontradoExcecao.class, () ->
                        this.profissionalObterFuncao.obterProfissionais(profissionalControladorObterRequisicao));

        verify(this.profissionalRepositorio, times(1)).findAll(Mockito.any(Specification.class));

        assertTrue(naoEncontradoExcecao.getMessage().contains("Profissional não encontrado"));
    }
}
