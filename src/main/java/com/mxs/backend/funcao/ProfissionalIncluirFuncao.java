package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ContatoConversor;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorCriarRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorCriarResposta;
import com.mxs.backend.tipo.CargoTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Classe responsável pelo caso de uso de inclusão da entidade Profissional.
 */
@Service
public class ProfissionalIncluirFuncao {

    @Autowired
    private ContatoConversor contatoConversor;
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
                ProfissionalModelo
                        .builder()
                        .nome(profissionalControladorCriarRequisicao.nome())
                        .nascimento(LocalDate.parse(profissionalControladorCriarRequisicao.nascimento()))
                        .cargoTipo(CargoTipo.valueOf(profissionalControladorCriarRequisicao.cargo()))
                        .build();

        var codigoProfissional = this.profissionalRepositorio.save(profissionalModelo).getCodigo();

        return ProfissionalControladorCriarResposta
                .builder()
                .codigo(codigoProfissional)
                .build();
    }
}
