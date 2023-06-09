package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.fachada.ProfissionalFachada;
import com.mxs.backend.repositorio.ContatoRepositorio;
import com.mxs.backend.requisicao.ContatoControladorCriarRequisicao;
import com.mxs.backend.resposta.ContatoControladorCriarResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe responsável pelo caso de uso de inclusão da entidade Contato.
 */
@Service
public class ContatoIncluirFuncao {

    @Autowired
    private ProfissionalFachada profissionalFachada;
    @Autowired
    private ContatoConversor contatoConversor;
    @Autowired
    private ContatoRepositorio contatoRepositorio;

    /**
     * Método responsável por incluir um Contato.
     *
     * @param contatoControladorCriarRequisicao objeto contendo os atributos do Contato
     * @return objeto contendo o código do Contato incluído
     */
    public ContatoControladorCriarResposta criarContato(ContatoControladorCriarRequisicao contatoControladorCriarRequisicao) {

        var profissionalModelo = this.profissionalFachada.obterProfissionalPorCodigo(contatoControladorCriarRequisicao.codigoProfissional());

        var contatoModelo = this.contatoConversor.controladorCriarRequisicaoEModeloParaModelo(contatoControladorCriarRequisicao, profissionalModelo);

        var codigoContato = this.contatoRepositorio.save(contatoModelo).getCodigo();

        return ContatoControladorCriarResposta.builder().codigo(codigoContato).build();
    }
}
