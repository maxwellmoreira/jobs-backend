package com.mxs.backend.excecao;

/**
 * Classe responsável por representar a exceção quando uma entidade não for encontrada no banco de dados.
 */
public class NaoEncontradoExcecao extends RuntimeException {
    public NaoEncontradoExcecao(String message) {
        super(message);
    }
}
