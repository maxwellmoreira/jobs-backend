package com.mxs.backend.funcao;

import com.mxs.backend.conversor.ProfissionalConversor;
import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.filtro.ProfissionalFiltro;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorObterRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorObterResposta;
import com.mxs.backend.tipo.CargoTipo;
import com.mxs.backend.tipo.StatusTipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.mxs.backend.padrao.MensagemPadrao.PROFISSIONAL_NAO_ENCONTRADO;

/**
 * Classe responsável pelo caso de uso de listagem da entidade Profissional.
 */
@Service
public class ProfissionalObterFuncao {

    @Autowired
    private ProfissionalConversor profissionalConversor;
    @Autowired
    private ProfissionalRepositorio profissionalRepositorio;

    /**
     * Método responsável por buscar uma lista de Profissionais com base em filtros.
     *
     * @param profissionalControladorObterRequisicao objeto contendo os filtros de busca
     * @return lista de Profissionais encontradas
     */
    public List<ProfissionalControladorObterResposta> obterProfissionais(ProfissionalControladorObterRequisicao profissionalControladorObterRequisicao) {

        Specification<ProfissionalModelo> profissionalModeloSpecification = Specification.where(null);

        // CODIGO
        if (profissionalControladorObterRequisicao.codigo() != null && !profissionalControladorObterRequisicao.codigo().isBlank()) {
            profissionalModeloSpecification = profissionalModeloSpecification.and(ProfissionalFiltro.filtrarPorCodigo(profissionalControladorObterRequisicao.codigo()));
        }

        // NOME
        if (profissionalControladorObterRequisicao.nome() != null && !profissionalControladorObterRequisicao.nome().isBlank()) {
            profissionalModeloSpecification = profissionalModeloSpecification.and(ProfissionalFiltro.filtrarPorNome(profissionalControladorObterRequisicao.nome()));
        }

        // NASCIMENTO
        if (profissionalControladorObterRequisicao.nascimento() != null && !profissionalControladorObterRequisicao.nascimento().isBlank()) {
            var nascimento = LocalDate.parse(profissionalControladorObterRequisicao.nascimento());
            profissionalModeloSpecification = profissionalModeloSpecification.and(ProfissionalFiltro.filtrarPorNascimento(nascimento));
        }

        // CARGO
        if (profissionalControladorObterRequisicao.cargo() != null && !profissionalControladorObterRequisicao.cargo().isBlank()) {
            profissionalModeloSpecification = profissionalModeloSpecification.and(ProfissionalFiltro.filtrarPorCargo(CargoTipo.valueOf(profissionalControladorObterRequisicao.cargo())));
        }

        // STATUS ATIVO
        profissionalModeloSpecification = profissionalModeloSpecification.and(ProfissionalFiltro.filtrarPorStatus(StatusTipo.ATIVO.getCodigo()));

        var profissionalModeloLista = this.profissionalRepositorio.findAll(profissionalModeloSpecification);

        if (profissionalModeloLista.isEmpty()) {
            throw new NaoEncontradoExcecao(PROFISSIONAL_NAO_ENCONTRADO);
        }

        return this.profissionalConversor.modeloListaParaControladorObterRespostaLista(profissionalModeloLista);
    }
}
