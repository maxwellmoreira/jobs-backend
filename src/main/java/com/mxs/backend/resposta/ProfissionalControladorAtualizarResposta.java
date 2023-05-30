package com.mxs.backend.resposta;

import lombok.Builder;

/**
 * Classe responsável por representar a resposta da atualização de um Profissional no controlador.
 */
@Builder
public record ProfissionalControladorAtualizarResposta(String codigo) {
}
