package com.mxs.backend.requisicao;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Classe responsável por representar a requisição de atualização de um Profissional no controlador.
 */
@Builder
public record ProfissionalControladorAtualizarRequisicao(
        @NotBlank(message = "Código do profissional é de preenchimento obrigatório")
        String codigo,
        @NotBlank(message = "Nome do Profissional é de preenchimento obrigatório")
        String nome,
        @NotBlank(message = "Cargo do Profissional é de preenchimento obrigatório")
        String cargo,
        String nascimento) {
}
