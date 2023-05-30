package com.mxs.backend.requisicao;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Classe responsável por representar a requisição de criação de um Contato no controlador.
 */
@Builder
public record ContatoControladorCriarRequisicao(
        @NotBlank(message = "Nome do contato é de preenchimento obrigatório")
        String nome,
        @NotBlank(message = "Contato é de preenchimento obrigatório")
        String contato,
        @NotBlank(message = "Código do profissional é de preenchimento obrigatório")
        String codigoProfissional) {
}
