package com.mxs.backend.repositorio;

import com.mxs.backend.modelo.ProfissionalModelo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe responsável por representar o repositório da entidade Profissional.
 */
@Repository
public interface ProfissionalRepositorio extends CrudRepository<ProfissionalModelo, Long>, JpaSpecificationExecutor<ProfissionalModelo> {
    Optional<ProfissionalModelo> findByCodigoEquals(String codigo);
}
