package com.mxs.backend.dto;

import com.mxs.backend.tipo.ExcecaoTipo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

import static com.mxs.backend.padrao.FormatoDataPadrao.FORMATO_PARA_EXCECAO;

@Data
public class ExcecaoDto {

    private String codigo;
    private String mensagem;
    private ExcecaoTipo tipo;
    private String quando;

    public ExcecaoDto() {
        this.codigo = UUID.randomUUID().toString();
        Timestamp agora = new Timestamp(System.currentTimeMillis());
        this.quando = FORMATO_PARA_EXCECAO.format(agora);
    }
}
