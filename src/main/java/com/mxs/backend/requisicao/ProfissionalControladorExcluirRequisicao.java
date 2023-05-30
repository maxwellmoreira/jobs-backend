package com.mxs.backend.requisicao;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Classe responsável por representar a requisição de exclusão de um Profissional no controlador.
 */
@Builder
public record ProfissionalControladorExcluirRequisicao(
        @NotBlank(message = "Código do profissional é de preenchimento obrigatório")
        String codigo) {
}
