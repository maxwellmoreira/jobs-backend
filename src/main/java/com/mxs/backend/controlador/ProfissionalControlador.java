package com.mxs.backend.controlador;

import com.mxs.backend.fachada.ProfissionalFachada;
import com.mxs.backend.requisicao.ProfissionalControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorCriarRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorExcluirRequisicao;
import com.mxs.backend.requisicao.ProfissionalControladorObterRequisicao;
import com.mxs.backend.resposta.ProfissionalControladorAtualizarResposta;
import com.mxs.backend.resposta.ProfissionalControladorCriarResposta;
import com.mxs.backend.resposta.ProfissionalControladorExcluirResposta;
import com.mxs.backend.resposta.ProfissionalControladorObterResposta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mxs.backend.padrao.UriPadrao.*;

/**
 * Classe responsável por representar o controlador de Profissional.
 */
@Validated
@Controller
@RequestMapping(value = PROFISSIONAIS_BASE)
public class ProfissionalControlador {

    @Autowired
    private ProfissionalFachada profissionalFachada;

    /**
     * Método responsável por criar um Profissional.
     *
     * @param profissionalControladorCriarRequisicao objeto contendo os campos de entrada para incluir um Profissional
     * @return objeto contendo o código do Profissional incluído
     */
    @PostMapping(PROFISSIONAL_INCLUIR)
    public ResponseEntity<ProfissionalControladorCriarResposta> criarProfissional(
            @Valid @RequestBody ProfissionalControladorCriarRequisicao profissionalControladorCriarRequisicao) {
        var profissionalIncluido = this.profissionalFachada.criarProfissional(profissionalControladorCriarRequisicao);
        return new ResponseEntity<>(profissionalIncluido, HttpStatus.CREATED);
    }

    /**
     * Método responsável por pesquisar Profissionais utilizando filtros.
     *
     * @param profissionalControladorObterRequisicao objeto contendo com campos de entrada para pesquisar Profissionais
     * @return lista de Profissionais encontrados
     */
    @GetMapping(PROFISSIONAL_BUSCAR_POR_FILTRO)
    public ResponseEntity<List<ProfissionalControladorObterResposta>> obterProfissional(
            @Valid ProfissionalControladorObterRequisicao profissionalControladorObterRequisicao) {
        var profissionaisEncontrados = this.profissionalFachada.obterProfissionais(profissionalControladorObterRequisicao);
        return new ResponseEntity<>(profissionaisEncontrados, HttpStatus.OK);
    }

    /**
     * Método responsável por atualizar um Profissional.
     *
     * @param codigo                                     identificador do Profissional
     * @param profissionalControladorAtualizarRequisicao objeto com campos de entrada para atualizar um Profissional
     * @return objeto contendo o código do Profissional atualizado
     */
    @PutMapping(PROFISSIONAL_ATUALIZAR)
    public ResponseEntity<ProfissionalControladorAtualizarResposta> atualizarProfissional(
            @NotBlank @PathVariable String codigo,
            @Valid @RequestBody ProfissionalControladorAtualizarRequisicao profissionalControladorAtualizarRequisicao) {
        var profissionalAtualizado = this.profissionalFachada.atualizarProfissional(profissionalControladorAtualizarRequisicao);
        return new ResponseEntity<>(profissionalAtualizado, HttpStatus.OK);
    }

    /**
     * Método responsável por excluir um Profissional.
     *
     * @param codigo identificador do Profissional
     * @return objeto contendo o código do Profissional excluído
     */
    @DeleteMapping(PROFISSIONAL_EXCLUIR)
    public ResponseEntity<ProfissionalControladorExcluirResposta> excluirProfissional(
            @NotBlank @PathVariable String codigo) {
        ProfissionalControladorExcluirRequisicao profissionalControladorExcluirRequisicao =
                ProfissionalControladorExcluirRequisicao
                        .builder()
                        .codigo(codigo)
                        .build();
        var profissionalExcluido = this.profissionalFachada.excluirProfissional(profissionalControladorExcluirRequisicao);
        return new ResponseEntity<>(profissionalExcluido, HttpStatus.OK);
    }
}
