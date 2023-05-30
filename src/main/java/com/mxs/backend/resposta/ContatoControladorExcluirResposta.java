package com.mxs.backend.resposta;

import lombok.Builder;

/**
 * Classe responsável por representar a resposta da exclusão de uma Contato no controlador.
 */
@Builder
public record ContatoControladorExcluirResposta(String codigo) {
}
