package com.mxs.backend.conversor;

import com.mxs.backend.dto.ContatoDto;
import com.mxs.backend.modelo.ContatoModelo;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.requisicao.ContatoControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ContatoControladorCriarRequisicao;
import com.mxs.backend.resposta.ContatoControladorObterResposta;
import com.mxs.backend.tipo.StatusTipo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por converter requisições, respostas e dto's relacionados com a entidade Contato.
 */
@Component
public class ContatoConversor {

    public List<ContatoDto> modeloListaParaDtoLista(List<ContatoModelo> contatoModeloLista) {
        List<ContatoDto> contatoDtoLista = new ArrayList<>();
        contatoModeloLista.forEach(contatoModelo -> {
            if (contatoModelo.getStatus().equals(StatusTipo.ATIVO.getCodigo())) {
                contatoDtoLista.add(modeloParaDto(contatoModelo));
            }
        });
        return contatoDtoLista;
    }

    public ContatoDto modeloParaDto(ContatoModelo contatoModelo) {
        return ContatoDto
                .builder()
                .codigo(contatoModelo.getCodigo())
                .nome(contatoModelo.getNome())
                .contato(contatoModelo.getContato())
                .build();
    }

    public ContatoModelo controladorCriarRequisicaoParaModelo(
            ContatoControladorCriarRequisicao contatoControladorCriarRequisicao, ProfissionalModelo profissionalModelo) {
        return ContatoModelo
                .builder()
                .nome(contatoControladorCriarRequisicao.nome())
                .contato(contatoControladorCriarRequisicao.contato())
                .profissionalModelo(profissionalModelo)
                .build();
    }

    public List<ContatoControladorObterResposta> modeloListaParaControladorObterRespostaLista(List<ContatoModelo> contatoModeloLista) {
        return contatoModeloLista.stream().map(this::modeloParaControladorObterResposta).collect(Collectors.toList());
    }

    public ContatoControladorObterResposta modeloParaControladorObterResposta(ContatoModelo contatoModelo) {
        return ContatoControladorObterResposta
                .builder()
                .codigo(contatoModelo.getCodigo())
                .nome(contatoModelo.getNome())
                .contato(contatoModelo.getContato())
                .codigoProfissional(contatoModelo.getProfissionalModelo().getCodigo())
                .build();
    }

    public ContatoModelo controladorAtualizarRequisicaoParaModelo(ContatoControladorAtualizarRequisicao contatoControladorAtualizarRequisicao,
                                                                  ContatoModelo contatoEncontradoNoBanco, ProfissionalModelo profissionalEncontraNoBanco) {
        ContatoModelo contatoModelo = contatoEncontradoNoBanco;
        contatoModelo.setNome(contatoControladorAtualizarRequisicao.nome());
        contatoModelo.setContato(contatoControladorAtualizarRequisicao.contato());
        contatoModelo.setProfissionalModelo(profissionalEncontraNoBanco);
        return contatoModelo;
    }
}
