package com.mxs.backend.modelo;

import jakarta.persistence.*;
import lombok.*;

/**
 * Classe responsável por representar a entidade Contato.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "contato")
public final class ContatoModelo extends AuditoriaModelo {
    @Column(name = "nome", nullable = false, length = 256)
    private String nome;
    @Column(name = "contato", nullable = false, length = 256)
    private String contato;
    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionalModelo profissionalModelo;
}
