package com.mxs.backend.requisicao;

import lombok.Builder;

/**
 * Classe responsável por representar a requisição de filtro para busca de Profissional no controlador.
 */
@Builder
public record ProfissionalControladorObterRequisicao(
        String codigo,
        String nome,
        String nascimento,
        String cargo) {
}