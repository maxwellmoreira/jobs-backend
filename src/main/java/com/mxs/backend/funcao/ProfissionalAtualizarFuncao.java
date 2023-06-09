package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ProfissionalConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorAtualizarRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorAtualizarResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mxs.backend.padrao.MensagemPadrao.PROFISSIONAL_NAO_ENCONTRADO;

/**
 * Classe responsável pelo caso de uso de atualização da entidade Profissional.
 */
@Service
public class ProfissionalAtualizarFuncao {

    @Autowired
    private ProfissionalConversor profissionalConversor;
    @Autowired
    private ProfissionalRepositorio profissionalRepositorio;

    /**
     * Método responsável por atualizar um Profissional.
     *
     * @param profissionalControladorAtualizarRequisicao objeto contendo os atributos que podem ser atualizados do Profissional
     * @return objeto contendo o código do Profissional atualizado
     * @throws NaoEncontradoExcecao lançada quando não for encontrada um Profissional
     */
    public ProfissionalControladorAtualizarResposta atualizarProfissional(ProfissionalControladorAtualizarRequisicao profissionalControladorAtualizarRequisicao) {

        var profissionalEncontradoNoBanco =
                this.profissionalRepositorio.findByCodigoEquals(profissionalControladorAtualizarRequisicao.codigo())
                        .orElseThrow(() -> new NaoEncontradoExcecao(PROFISSIONAL_NAO_ENCONTRADO));

        var profissionalModelo =
                this.profissionalConversor.controladorAtualizarRequisicaoEModeloParaModelo(
                        profissionalControladorAtualizarRequisicao, profissionalEncontradoNoBanco);

        var codigoProfissional = this.profissionalRepositorio.save(profissionalModelo).getCodigo();

        return ProfissionalControladorAtualizarResposta.builder().codigo(codigoProfissional).build();
    }
}
