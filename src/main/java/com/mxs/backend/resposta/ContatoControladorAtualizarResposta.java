package com.mxs.backend.resposta;

import lombok.Builder;

/**
 * Classe responsável por representar a resposta da atualização de uma Contato no controlador.
 */
@Builder
public record ContatoControladorAtualizarResposta(String codigo) {
}
