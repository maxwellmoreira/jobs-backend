package com.mxs.backend.requisicao;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Classe responsável por representar a requisição de exclusão de um Contato no controlador.
 */
@Builder
public record ContatoControladorExcluirRequisicao(
        @NotBlank(message = "Código do contato é de preenchimento obrigatório")
        String codigo) {
}
