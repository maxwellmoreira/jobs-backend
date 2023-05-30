package com.mxs.backend.resposta;

import lombok.Builder;

/**
 * Classe responsável por representar a resposta da criação de um Profissional no controlador.
 */
@Builder
public record ProfissionalControladorCriarResposta(String codigo) {
}
