package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.fachada.ProfissionalFachada;
import com.mxs.backend.repositorio.ContatoRepositorio;
import com.mxs.backend.requisicao.ContatoControladorAtualizarRequisicao;
import com.mxs.backend.resposta.ContatoControladorAtualizarResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mxs.backend.padrao.MensagemPadrao.CONTATO_NAO_ENCONTRADO;

/**
 * Classe responsável pelo caso de uso de atualização da entidade Contato.
 */
@Service
public class ContatoAtualizarFuncao {

    @Autowired
    private ProfissionalFachada profissionalFachada;
    @Autowired
    private ContatoConversor contatoConversor;
    @Autowired
    private ContatoRepositorio contatoRepositorio;

    /**
     * Método responsável por atualizar um Contato.
     *
     * @param contatoControladorAtualizarRequisicao objeto contendo os atributos que podem ser atualizados
     * @return objeto contendo o código do Contato atualizado
     * @throws NaoEncontradoExcecao lançada quando não for encontrado um Contato
     */
    public ContatoControladorAtualizarResposta atualizarContato(ContatoControladorAtualizarRequisicao contatoControladorAtualizarRequisicao) {

        var contatoEncontradoNoBanco =
                this.contatoRepositorio.findByCodigoEquals(contatoControladorAtualizarRequisicao.codigo())
                        .orElseThrow(() -> new NaoEncontradoExcecao(CONTATO_NAO_ENCONTRADO));

        var profissionalEncontradoNoBanco =
                this.profissionalFachada.obterProfissionalPorCodigo(
                        contatoControladorAtualizarRequisicao.codigoProfissional());

        var contatoModelo =
                this.contatoConversor.controladorAtualizarRequisicaoParaModelo(
                        contatoControladorAtualizarRequisicao, contatoEncontradoNoBanco, profissionalEncontradoNoBanco);

        var codigoContato = this.contatoRepositorio.save(contatoModelo).getCodigo();

        return ContatoControladorAtualizarResposta
                .builder()
                .codigo(codigoContato)
                .build();
    }
}
