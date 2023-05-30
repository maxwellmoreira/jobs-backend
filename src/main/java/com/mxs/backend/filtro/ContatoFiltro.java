package com.mxs.backend.filtro;

import com.mxs.backend.modelo.ContatoModelo;
import com.mxs.backend.modelo.ProfissionalModelo;
import org.springframework.data.jpa.domain.Specification;

/**
 * Classe responsável pelos filtros que poderão ser utilizados em consultas da entidade Contato.
 */
public class ContatoFiltro {

    // CODIGO
    public static Specification<ContatoModelo> filtrarPorCodigo(String codigo) {
        return (root, query, builder) -> builder.equal(root.get("codigo"), codigo);
    }

    // NOME
    public static Specification<ContatoModelo> filtrarPorNome(String descricao) {
        return (root, query, builder) -> builder.equal(root.get("nome"), descricao);
    }

    // CONTATO
    public static Specification<ContatoModelo> filtrarPorContato(String contato) {
        return (root, query, builder) -> builder.equal(root.get("contato"), contato);
    }

    // PROFISSIONAL
    public static Specification<ContatoModelo> filtrarPorProfissional(ProfissionalModelo profissionalModelo) {
        return (root, query, builder) -> builder.equal(root.get("profissionalModelo"), profissionalModelo);
    }

    // STATUS
    public static Specification<ContatoModelo> filtrarPorStatus(String status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }
}
