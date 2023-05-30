package com.mxs.backend.requisicao;

import lombok.Builder;

/**
 * Classe responsável por representar a requisição de filtro para busca de Contatos no controlador.
 */
@Builder
public record ContatoControladorObterRequisicao(
        String codigo,
        String nome,
        String contato,
        String codigoProfissional) {
}
