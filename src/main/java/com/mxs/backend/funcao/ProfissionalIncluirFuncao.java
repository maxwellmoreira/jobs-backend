package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ProfissionalConversor;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorCriarRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorCriarResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe responsável pelo caso de uso de inclusão da entidade Profissional.
 */
@Service
public class ProfissionalIncluirFuncao {

    @Autowired
    private ProfissionalConversor profissionalConversor;
    @Autowired
    private ProfissionalRepositorio profissionalRepositorio;

    /**
     * Método responsável por incluir um Profissional.
     *
     * @param profissionalControladorCriarRequisicao objeto contendo os atributos do Profissional
     * @return objeto contendo o código de identificação da Profissional incluído
     */
    public ProfissionalControladorCriarResposta criarProfissional(ProfissionalControladorCriarRequisicao profissionalControladorCriarRequisicao) {

        var profissionalModelo =
                this.profissionalConversor.controladorCriarRequisicaoParaModelo(profissionalControladorCriarRequisicao);

        var codigoProfissional = this.profissionalRepositorio.save(profissionalModelo).getCodigo();

        return ProfissionalControladorCriarResposta
                .builder()
                .codigo(codigoProfissional)
                .build();
    }
}
