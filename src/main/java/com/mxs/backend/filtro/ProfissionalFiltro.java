package com.mxs.backend.filtro;

import com.mxs.backend.modelo.ProfissionalModelo;
import com.mxs.backend.tipo.CargoTipo;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

/**
 * Classe responsável pelos filtros que poderão ser utilizados em consultas da entidade Profissional.
 */
public class ProfissionalFiltro {
    // CODIGO
    public static Specification<ProfissionalModelo> filtrarPorCodigo(String codigo) {
        return (root, query, builder) -> builder.equal(root.get("codigo"), codigo);
    }

    // NOME
    public static Specification<ProfissionalModelo> filtrarPorNome(String codigo) {
        return (root, query, builder) -> builder.equal(root.get("nome"), codigo);
    }

    // NASCIMENTO
    public static Specification<ProfissionalModelo> filtrarPorNascimento(LocalDate data) {
        return (root, query, builder) -> builder.equal(root.get("nascimento"), data);
    }

    // CARGO
    public static Specification<ProfissionalModelo> filtrarPorCargo(CargoTipo cargoTipo) {
        return (root, query, builder) -> builder.equal(root.get("cargoTipo"), cargoTipo);
    }

    // STATUS
    public static Specification<ProfissionalModelo> filtrarPorStatus(String status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }
}
