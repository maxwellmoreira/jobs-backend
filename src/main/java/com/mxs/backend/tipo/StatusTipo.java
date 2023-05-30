package com.mxs.backend.tipo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum StatusTipo {

    ATIVO("A"),
    INATIVO("I");

    private String codigo;

    public static StatusTipo of(String codigo) {
        return Stream.of(StatusTipo.values())
                .filter(status -> status.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
