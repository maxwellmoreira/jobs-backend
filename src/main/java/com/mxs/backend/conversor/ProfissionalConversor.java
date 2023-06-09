package com.mxs.backend.conversor;

import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.requisicao.ProfissionalControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorCriarRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorObterResposta;
import com.mxs.backend.tipo.CargoTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por converter requisições, respostas e dto's relacionados com a entidade Profissional.
 */
@Component
public class ProfissionalConversor {

    @Autowired
    private ContatoConversor contatoConversor;

    public ProfissionalModelo controladorCriarRequisicaoParaModelo(ProfissionalControladorCriarRequisicao profissionalControladorCriarRequisicao) {
        return ProfissionalModelo
                .builder()
                .nome(profissionalControladorCriarRequisicao.nome())
                .nascimento(LocalDate.parse(profissionalControladorCriarRequisicao.nascimento()))
                .cargoTipo(CargoTipo.valueOf(profissionalControladorCriarRequisicao.cargo()))
                .build();
    }

    public List<ProfissionalControladorObterResposta> modeloListaParaControladorObterRespostaLista(List<ProfissionalModelo> modeloLista) {
        return modeloLista.stream().map(this::modeloParaControladorObterResposta).collect(Collectors.toList());
    }

    public ProfissionalControladorObterResposta modeloParaControladorObterResposta(ProfissionalModelo profissionalModelo) {
        return ProfissionalControladorObterResposta
                .builder()
                .codigo(profissionalModelo.getCodigo())
                .nome(profissionalModelo.getNome())
                .nascimento(profissionalModelo.getNascimento().toString())
                .cargo(profissionalModelo.getCargoTipo().name())
                .contatos(this.contatoConversor.modeloListaParaDtoLista(profissionalModelo.getContatoModeloLista()))
                .build();
    }

    public ProfissionalModelo controladorAtualizarRequisicaoEModeloParaModelo(ProfissionalControladorAtualizarRequisicao profissionalControladorAtualizarRequisicao,
                                                                       ProfissionalModelo profissionalEncontradaNoBanco) {
        ProfissionalModelo profissionalModelo = profissionalEncontradaNoBanco;
        profissionalModelo.setNome(profissionalControladorAtualizarRequisicao.nome());
        profissionalModelo.setNascimento(LocalDate.parse(profissionalControladorAtualizarRequisicao.nascimento()));
        profissionalModelo.setCargoTipo(CargoTipo.valueOf(profissionalControladorAtualizarRequisicao.cargo()));
        return profissionalModelo;
    }
}
