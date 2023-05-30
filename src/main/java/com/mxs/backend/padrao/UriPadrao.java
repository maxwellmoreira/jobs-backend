package com.mxs.backend.padrao;

/**
 * Classe responsável pelas definições de URI na camada controladora.
 */
public class UriPadrao {

    public static final String URI_BASE_V1 = "api/v1/";

    // ------------------------ DEFINIÇÕES DE PROFISSIONAL ------------------------ //
    public static final String PROFISSIONAIS_BASE = URI_BASE_V1 + "profissionais";
    public static final String PROFISSIONAL_INCLUIR = "profissional";
    public static final String PROFISSIONAL_BUSCAR_POR_FILTRO = "buscar-por-filtro";
    public static final String PROFISSIONAL_ATUALIZAR = "profissional/{codigo}";
    public static final String PROFISSIONAL_EXCLUIR = "profissional/{codigo}";

    // ------------------------ DEFINIÇÕES DE CONTATO ------------------------ //
    public static final String CONTATOS_BASE = URI_BASE_V1 + "contatos";
    public static final String CONTATO_INCLUIR = "contato";
    public static final String CONTATO_BUSCAR_POR_FILTRO = "buscar-por-filtro";
    public static final String CONTATO_ATUALIZAR = "contato/{codigo}";
    public static final String CONTATO_EXCLUIR = "contato/{codigo}";
}
