package com.mxs.backend.resposta;

import com.mxs.backend.dto.ContatoDto;
import lombok.Builder;

import java.util.List;

/**
 * Classe responsável por representar a resposta de uma listagem de Profissionais no controlador.
 */
@Builder
public record ProfissionalControladorObterResposta(
        String codigo,
        String nome,
        String cargo,
        String nascimento,
        List<ContatoDto> contatos) {
}
