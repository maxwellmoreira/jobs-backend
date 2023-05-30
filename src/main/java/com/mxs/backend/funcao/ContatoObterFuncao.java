package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.fachada.ProfissionalFachada;
import com.mxs.backend.filtro.ContatoFiltro;
import com.mxs.backend.modelo.ContatoModelo;
import com.mxs.backend.repositorio.ContatoRepositorio;
import com.mxs.backend.requisicao.ContatoControladorObterRequisicao;
import com.mxs.backend.resposta.ContatoControladorObterResposta;
import com.mxs.backend.tipo.StatusTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mxs.backend.padrao.MensagemPadrao.CONTATO_NAO_ENCONTRADO;

/**
 * Classe responsável pelo caso de uso de busca da entidade Contato.
 */
@Service
public class ContatoObterFuncao {

    @Autowired
    private ProfissionalFachada profissionalFachada;
    @Autowired
    private ContatoConversor contatoConversor;
    @Autowired
    private ContatoRepositorio contatoRepositorio;

    /**
     * Método responsável por buscar uma lista de Contatos com base em filtros.
     *
     * @param contatoControladorObterRequisicao objeto contendo os filtros de busca
     * @return lista de Contatos encontrados
     */
    public List<ContatoControladorObterResposta> obterContatos(ContatoControladorObterRequisicao contatoControladorObterRequisicao) {

        Specification<ContatoModelo> contatoModeloSpecification = Specification.where(null);

        // CODIGO
        if (contatoControladorObterRequisicao.codigo() != null && !contatoControladorObterRequisicao.codigo().isBlank()) {
            contatoModeloSpecification = contatoModeloSpecification.and(ContatoFiltro.filtrarPorCodigo(contatoControladorObterRequisicao.codigo()));
        }

        // NOME
        if (contatoControladorObterRequisicao.nome() != null && !contatoControladorObterRequisicao.nome().isBlank()) {
            contatoModeloSpecification = contatoModeloSpecification.and(ContatoFiltro.filtrarPorNome(contatoControladorObterRequisicao.nome()));
        }

        // CONTATO
        if (contatoControladorObterRequisicao.contato() != null && !contatoControladorObterRequisicao.contato().isBlank()) {
            contatoModeloSpecification = contatoModeloSpecification.and(ContatoFiltro.filtrarPorContato(contatoControladorObterRequisicao.contato()));
        }

        // PROFISSIONAL
        if (contatoControladorObterRequisicao.codigoProfissional() != null && !contatoControladorObterRequisicao.codigoProfissional().isBlank()) {
            var profissionalModel = this.profissionalFachada.obterProfissionalPorCodigo(contatoControladorObterRequisicao.codigoProfissional());
            contatoModeloSpecification = contatoModeloSpecification.and(ContatoFiltro.filtrarPorProfissional(profissionalModel));
        }

        // STATUS ATIVO
        contatoModeloSpecification = contatoModeloSpecification.and(ContatoFiltro.filtrarPorStatus(StatusTipo.ATIVO.getCodigo()));

        var contatoModeloLista = this.contatoRepositorio.findAll(contatoModeloSpecification);

        if (contatoModeloLista.isEmpty()) {
            throw new NaoEncontradoExcecao(CONTATO_NAO_ENCONTRADO);
        }

        return this.contatoConversor.modeloListaParaControladorObterRespostaLista(contatoModeloLista);
    }
}
