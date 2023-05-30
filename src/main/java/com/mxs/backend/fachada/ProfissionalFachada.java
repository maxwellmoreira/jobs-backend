package com.mxs.backend.fachada;

import com.mxs.backend.excecao.NaoEncontradoExcecao;
import com.mxs.backend.funcao.ProfissionalAtualizarFuncao;
import com.mxs.backend.funcao.ProfissionalExcluirFuncao;
import com.mxs.backend.funcao.ProfissionalIncluirFuncao;
import com.mxs.backend.funcao.ProfissionalObterFuncao;
import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.repositorio.ProfissionalRepositorio;
import com.mxs.backend.requisicao.ProfissionalControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorCriarRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorExcluirRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorObterRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorAtualizarResposta;
import com.mxs.backend.resposta.ProfissionalControladorCriarResposta;
import com.mxs.backend.resposta.ProfissionalControladorExcluirResposta;
import com.mxs.backend.resposta.ProfissionalControladorObterResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mxs.backend.padrao.MensagemPadrao.PROFISSIONAL_NAO_ENCONTRADO;

/**
 * Classe responsável por agrupar todos os casos de uso do domínio Profissional.
 */
@Service
public class ProfissionalFachada {

    @Autowired
    private ProfissionalIncluirFuncao profissionalIncluirFuncao;
    @Autowired
    private ProfissionalObterFuncao profissionalObterFuncao;
    @Autowired
    private ProfissionalAtualizarFuncao profissionalAtualizarFuncao;
    @Autowired
    private ProfissionalExcluirFuncao profissionalExcluirFuncao;
    @Autowired
    private ProfissionalRepositorio profissionalRepositorio;

    /**
     * Método responsável por chamar o caso de uso referente à criação de Profissional.
     *
     * @param profissionalControladorCriarRequisicaoLista lista de Profissionais
     * @return lista de códigos das Profissionais incluídas
     */
    public List<ProfissionalControladorCriarResposta> criarProfissionais(List<ProfissionalControladorCriarRequisicao> profissionalControladorCriarRequisicaoLista) {
        return profissionalControladorCriarRequisicaoLista.stream().map(this::criarProfissional).collect(Collectors.toList());
    }

    /**
     * Método responsável por chamar o caso de uso referente à criação de Profissional.
     *
     * @param profissionalControladorCriarRequisicao objeto contendo os atributos da Profissional
     * @return objeto contendo o código de identificação da Profissional incluída
     */
    public ProfissionalControladorCriarResposta criarProfissional(ProfissionalControladorCriarRequisicao profissionalControladorCriarRequisicao) {
        return this.profissionalIncluirFuncao.criarProfissional(profissionalControladorCriarRequisicao);
    }

    /**
     * Método responsável por chamar o caso de uso referente à pesquisa de Profissionais.
     *
     * @param profissionalControladorObterRequisicao objeto contendo os filtros de busca
     * @return lista de Profissionais encontradas
     */
    public List<ProfissionalControladorObterResposta> obterProfissionais(ProfissionalControladorObterRequisicao profissionalControladorObterRequisicao) {
        return this.profissionalObterFuncao.obterProfissionais(profissionalControladorObterRequisicao);
    }

    /**
     * Método responsável por pesquisar por uma Profissional.
     *
     * @param codigoProfissional código da Profissional
     * @return objeto Profissional encontrado
     * @throws NaoEncontradoExcecao lançada quando não for encontrada uma Profissional
     */
    public ProfissionalModelo obterProfissionalPorCodigo(String codigoProfissional) {
        return this.profissionalRepositorio.findByCodigoEquals(codigoProfissional).orElseThrow(() -> new NaoEncontradoExcecao(PROFISSIONAL_NAO_ENCONTRADO));
    }

    /**
     * Método responsável por chamar o caso de uso referente à atualização de Profissional.
     *
     * @param profissionalControladorAtualizarRequisicao objeto contendo os atributos que podem ser atualizados da Profissional
     * @return objeto contendo o código da Profissional atualizada
     */
    public ProfissionalControladorAtualizarResposta atualizarProfissional(ProfissionalControladorAtualizarRequisicao profissionalControladorAtualizarRequisicao) {
        return this.profissionalAtualizarFuncao.atualizarProfissional(profissionalControladorAtualizarRequisicao);
    }

    /**
     * Método responsável por chamar o caso de uso referente à exclusão de Profissional.
     *
     * @param profissionalControladorExcluirRequisicao objeto contendo o código da Profissional
     * @return objeto contendo o código da Profissional excluída
     */
    public ProfissionalControladorExcluirResposta excluirProfissional(ProfissionalControladorExcluirRequisicao profissionalControladorExcluirRequisicao) {
        return this.profissionalExcluirFuncao.excluirProfissional(profissionalControladorExcluirRequisicao);
    }
}
