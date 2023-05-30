package com.mxs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ContatoDto {
    private String codigo;
    private String nome;
    private String contato;
}
