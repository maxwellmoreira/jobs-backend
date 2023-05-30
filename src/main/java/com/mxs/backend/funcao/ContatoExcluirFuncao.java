package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.repositorio.ContatoRepositorio;
import com.mxs.backend.requisicao.ContatoControladorExcluirRequisicao;
import com.mxs.backend.resposta.ContatoControladorExcluirResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mxs.backend.padrao.MensagemPadrao.CONTATO_NAO_ENCONTRADO;

/**
 * Classe responsável pelo caso de uso de exclusão da entidade Contato.
 */
@Service
public class ContatoExcluirFuncao {

    @Autowired
    private ContatoConversor contatoConversor;
    @Autowired
    private ContatoRepositorio contatoRepositorio;

    /**
     * Método responsável por excluir um Contato.
     * Ocorrerá uma exclusão lógica (status = "I").
     *
     * @param contatoControladorExcluirRequisicao objeto contendo o código do Contato
     * @return objeto contendo o código do contato excluído
     * @throws NaoEncontradoExcecao lançada quando não for encontrado um Contato
     */
    public ContatoControladorExcluirResposta excluirContato(ContatoControladorExcluirRequisicao contatoControladorExcluirRequisicao) {

        var contatoEncontradoNoBanco =
                this.contatoRepositorio.findByCodigoEquals(contatoControladorExcluirRequisicao.codigo())
                        .orElseThrow(() -> new NaoEncontradoExcecao(CONTATO_NAO_ENCONTRADO));

        contatoEncontradoNoBanco.inativarRegistro();

        var codigoContato = this.contatoRepositorio.save(contatoEncontradoNoBanco).getCodigo();

        return ContatoControladorExcluirResposta
                .builder()
                .codigo(codigoContato)
                .build();
    }
}
