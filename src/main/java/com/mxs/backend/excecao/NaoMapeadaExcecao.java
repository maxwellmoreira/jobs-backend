package com.mxs.backend.excecao;

/**
 * Classe responsável por representar a exceção de uma causa ainda não mapeada.
 */
public class NaoMapeadaExcecao extends RuntimeException {
    public NaoMapeadaExcecao(String message) {
        super(message);
    }
}
