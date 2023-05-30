package com.mxs.backend.resposta;

import lombok.Builder;

/**
 * Classe responsável por representar a resposta da exclusão de um Profissional no controlador.
 */
@Builder
public record ProfissionalControladorExcluirResposta(String codigo) {
}
