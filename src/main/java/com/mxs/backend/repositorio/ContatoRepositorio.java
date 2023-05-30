package com.mxs.backend.repositorio;

import com.mxs.backend.modelo.ContatoModelo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Classe responsável por representar o repositório da entidade Contato.
 */
@Repository
public interface ContatoRepositorio extends CrudRepository<ContatoModelo, Long>, JpaSpecificationExecutor<ContatoModelo> {
    Optional<ContatoModelo> findByCodigoEquals(String codigo);
}
