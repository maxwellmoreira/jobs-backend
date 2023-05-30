package com.mxs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProfissionalDto {
    private String data;
    private String statusProfissional;
    private List<ContatoDto> contatos;
}
