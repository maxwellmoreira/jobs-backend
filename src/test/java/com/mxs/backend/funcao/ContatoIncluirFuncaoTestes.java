package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.fachada.ProfissionalFachada;
import com.mxs.backend.modelo.ContatoModelo;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ContatoRepositorio;
import com.mxs.backend.requisicao.ContatoControladorCriarRequisicao;
import com.mxs.backend.tipo.CargoTipo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContatoIncluirFuncaoTestes {

    @InjectMocks
    private ContatoIncluirFuncao contatoIncluirFuncao;
    @Mock
    private ProfissionalFachada profissionalFachada;
    @Mock
    private ContatoConversor contatoConversor;
    @Mock
    private ContatoRepositorio contatoRepositorio;

    @DisplayName("Teste de inclusão com sucesso.")
    @Test
    void testarInclusaoComSucesso() {

        var nome = "José";
        var contato = "(11) 9 9999-9999";
        var codigoProfissional = UUID.randomUUID().toString();
        var nascimento = LocalDate.now();
        var cargo = CargoTipo.DESENVOLVEDOR;

        ContatoControladorCriarRequisicao contatoControladorCriarRequisicao =
                ContatoControladorCriarRequisicao
                        .builder()
                        .nome(nome)
                        .contato(contato)
                        .codigoProfissional(codigoProfissional)
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

        when(this.profissionalFachada.obterProfissionalPorCodigo(contatoControladorCriarRequisicao.codigoProfissional())).thenReturn(profissionalModelo);
        when(this.contatoConversor.controladorCriarRequisicaoEModeloParaModelo(contatoControladorCriarRequisicao, profissionalModelo)).thenReturn(contatoModelo);
        when(this.contatoRepositorio.save(any(ContatoModelo.class))).thenReturn(contatoModelo);

        this.contatoIncluirFuncao.criarContato(contatoControladorCriarRequisicao);
    }
}
