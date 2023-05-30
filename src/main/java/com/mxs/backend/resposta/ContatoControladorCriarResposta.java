package com.mxs.backend.resposta;

import lombok.Builder;

/**
 * Classe responsável por representar a resposta da criação de uma Contato no controlador.
 */
@Builder
public record ContatoControladorCriarResposta(String codigo) {
}
