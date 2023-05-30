package com.mxs.backend.controlador;

import com.mxs.backend.fachada.ContatoFachada;
import com.mxs.backend.requisicao.ContatoControladorAtualizarRequisicao;
import com.mxs.backend.requisicao.ContatoControladorCriarRequisicao;
import com.mxs.backend.requisicao.ContatoControladorExcluirRequisicao;
import com.mxs.backend.requisicao.ContatoControladorObterRequisicao;
import com.mxs.backend.resposta.ContatoControladorAtualizarResposta;
import com.mxs.backend.resposta.ContatoControladorCriarResposta;
import com.mxs.backend.resposta.ContatoControladorExcluirResposta;
import com.mxs.backend.resposta.ContatoControladorObterResposta;
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

;

/**
 * Classe responsável por representar o controlador de Contato.
 */
@Validated
@Controller
@RequestMapping(value = CONTATOS_BASE)
public class ContatoControlador {

    @Autowired
    private ContatoFachada contatoFachada;

    /**
     * Método responsável por criar um Contato.
     *
     * @param contatoControladorCriarRequisicao objeto contendo os campos de entrada para incluir um Contato
     * @return objeto contendo o código do Contato incluída
     */
    @PostMapping(CONTATO_INCLUIR)
    public ResponseEntity<ContatoControladorCriarResposta> criarContato(
            @Valid @RequestBody ContatoControladorCriarRequisicao contatoControladorCriarRequisicao) {
        var contatoCriada = this.contatoFachada.criarContato(contatoControladorCriarRequisicao);
        return new ResponseEntity<>(contatoCriada, HttpStatus.CREATED);
    }

    /**
     * Método responsável por pesquisar Contatos utilizando filtros.
     *
     * @param contatoControladorObterRequisicao objeto contendo com campos de entrada para pesquisar Contatos
     * @return lista de Contatos encontrados
     */
    @GetMapping(CONTATO_BUSCAR_POR_FILTRO)
    public ResponseEntity<List<ContatoControladorObterResposta>> obterContatos(
            @Valid ContatoControladorObterRequisicao contatoControladorObterRequisicao) {
        var contatosEncontradas = this.contatoFachada.obterContatos(contatoControladorObterRequisicao);
        return new ResponseEntity<>(contatosEncontradas, HttpStatus.OK);
    }

    /**
     * Método responsável por atualizar um Contato.
     *
     * @param codigo                                identificador do Contato
     * @param contatoControladorAtualizarRequisicao objeto contendo os campos de entrada para atualizar um Contato
     * @return objeto contendo o código do Contato atualizado
     */
    @PutMapping(CONTATO_ATUALIZAR)
    public ResponseEntity<ContatoControladorAtualizarResposta> atualizarContato(
            @NotBlank @PathVariable String codigo,
            @Valid @RequestBody ContatoControladorAtualizarRequisicao contatoControladorAtualizarRequisicao) {
        var contatoAtualizada = this.contatoFachada.atualizarContato(contatoControladorAtualizarRequisicao);
        return new ResponseEntity<>(contatoAtualizada, HttpStatus.OK);
    }

    /**
     * Método responsável por excluir um Contato.
     *
     * @param codigo identificador do Contato
     * @return objeto contendo o código do Contato excluído
     */
    @DeleteMapping(CONTATO_EXCLUIR)
    public ResponseEntity<ContatoControladorExcluirResposta> excluirContato(
            @NotBlank @PathVariable String codigo) {
        ContatoControladorExcluirRequisicao contatoControladorExcluirRequisicao =
                ContatoControladorExcluirRequisicao
                        .builder()
                        .codigo(codigo)
                        .build();
        var contatoExcluida = this.contatoFachada.excluirContato(contatoControladorExcluirRequisicao);
        return new ResponseEntity<>(contatoExcluida, HttpStatus.OK);
    }
}
