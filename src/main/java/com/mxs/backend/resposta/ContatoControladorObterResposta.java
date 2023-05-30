package com.mxs.backend.resposta;

import lombok.Builder;

/**
 * Classe responsável por representar a resposta de uma listagem de Contatos no controlador.
 */
@Builder
public record ContatoControladorObterResposta(
        String codigo,
        String nome,
        String contato,
        String codigoProfissional) {
}
