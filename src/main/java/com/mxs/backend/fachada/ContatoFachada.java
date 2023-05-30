package com.mxs.backend.fachada;

import com.mxs.backend.funcao.ContatoAtualizarFuncao;
import com.mxs.backend.funcao.ContatoExcluirFuncao;
import com.mxs.backend.funcao.ContatoIncluirFuncao;
import com.mxs.backend.funcao.ContatoObterFuncao;
import com.mxs.backend.requisicao.ContatoControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ContatoControladorCriarRequisicao;
import com.mxs.backend.requisicao.ContatoControladorExcluirRequisicao;
import com.mxs.backend.requisicao.ContatoControladorObterRequisicao;
import com.mxs.backend.resposta.ContatoControladorAtualizarResposta;
import com.mxs.backend.resposta.ContatoControladorCriarResposta;
import com.mxs.backend.resposta.ContatoControladorExcluirResposta;
import com.mxs.backend.resposta.ContatoControladorObterResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por agrupar todos os casos de uso do domínio Contato.
 */
@Service
public class ContatoFachada {

    @Autowired
    private ContatoIncluirFuncao contatoIncluirFuncao;
    @Autowired
    private ContatoObterFuncao contatoObterFuncao;
    @Autowired
    private ContatoAtualizarFuncao contatoAtualizarFuncao;
    @Autowired
    private ContatoExcluirFuncao contatoExcluirFuncao;

    /**
     * Método responsável por chamar o caso de uso referente à criação de Contato.
     *
     * @param contatoControladorCriarRequisicaoLista lista de Contatos
     * @return lista de códigos dos Contatos incluídos
     */
    public List<ContatoControladorCriarResposta> criarCOntatos(List<ContatoControladorCriarRequisicao> contatoControladorCriarRequisicaoLista) {
        return contatoControladorCriarRequisicaoLista.stream().map(this::criarContato).collect(Collectors.toList());
    }

    /**
     * Método responsável por chamar o caso de uso referente à criação de Contato.
     *
     * @param contatoControladorCriarRequisicao objeto contendo os atributos do Contato
     * @return objeto contendo o código de identificação do Contato incluído
     */
    public ContatoControladorCriarResposta criarContato(ContatoControladorCriarRequisicao contatoControladorCriarRequisicao) {
        return this.contatoIncluirFuncao.criarContato(contatoControladorCriarRequisicao);
    }

    /**
     * Método responsável por chamar o caso de uso referente à pesquisa de Contatos.
     *
     * @param contatoControladorObterRequisicao objeto contendo os filtros de busca
     * @return lista de Contatos encontrados
     */
    public List<ContatoControladorObterResposta> obterContatos(ContatoControladorObterRequisicao contatoControladorObterRequisicao) {
        return this.contatoObterFuncao.obterContatos(contatoControladorObterRequisicao);
    }

    /**
     * Método responsável por chamar o caso de uso referente à atualização de Contato.
     *
     * @param contatoControladorAtualizarRequisicaoLista lista de Contatos
     * @return lista de códigos dos Contatos atualizados
     */
    public List<ContatoControladorAtualizarResposta> atualizarContatos(List<ContatoControladorAtualizarRequisicao> contatoControladorAtualizarRequisicaoLista) {
        return contatoControladorAtualizarRequisicaoLista.stream().map(this::atualizarContato).collect(Collectors.toList());
    }

    /**
     * Método responsável por chamar o caso de uso referente à atualização de Contato.
     *
     * @param contatoControladorAtualizarRequisicao objeto contendo os atributos que podem ser atualizados de Contato
     * @return objeto contendo o código de Contato atualizado
     */
    public ContatoControladorAtualizarResposta atualizarContato(ContatoControladorAtualizarRequisicao contatoControladorAtualizarRequisicao) {
        return this.contatoAtualizarFuncao.atualizarContato(contatoControladorAtualizarRequisicao);
    }

    /**
     * Método responsável por chamar o caso de uso referente à exclusão de Contato.
     *
     * @param contatoControladorExcluirRequisicao objeto contendo o código do Contato
     * @return objeto contendo o código do Contato excluído
     */
    public ContatoControladorExcluirResposta excluirContato(ContatoControladorExcluirRequisicao contatoControladorExcluirRequisicao) {
        return this.contatoExcluirFuncao.excluirContato(contatoControladorExcluirRequisicao);
    }
}
