package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ProfissionalConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorExcluirRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorExcluirResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mxs.backend.padrao.MensagemPadrao.PROFISSIONAL_NAO_ENCONTRADO;

/**
 * Classe responsável pelo caso de uso de exclusão da entidade Profissional.
 */
@Service
public class ProfissionalExcluirFuncao {

    @Autowired
    private ProfissionalConversor profissionalConversor;
    @Autowired
    private ProfissionalRepositorio profissionalRepositorio;

    /**
     * Método responsável por excluir um Profissional.
     * Ocorrerá uma exclusão lógica (status = "I").
     *
     * @param profissionalControladorExcluirRequisicao objeto contendo o código do Profissional
     * @return objeto contendo o código do Profissional excluído
     * @throws NaoEncontradoExcecao lançada quando não for encontrado um Profissional
     */
    public ProfissionalControladorExcluirResposta excluirProfissional(ProfissionalControladorExcluirRequisicao profissionalControladorExcluirRequisicao) {

        var profissionalEncontradoNoBanco =
                this.profissionalRepositorio.findByCodigoEquals(profissionalControladorExcluirRequisicao.codigo())
                        .orElseThrow(() -> new NaoEncontradoExcecao(PROFISSIONAL_NAO_ENCONTRADO));

        profissionalEncontradoNoBanco.inativarRegistro();

        var codigoProfissional = this.profissionalRepositorio.save(profissionalEncontradoNoBanco).getCodigo();

        return ProfissionalControladorExcluirResposta
                .builder()
                .codigo(codigoProfissional)
                .build();
    }
}
