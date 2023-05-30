package com.mxs.backend.modelo;

import com.mxs.backend.tipo.CargoTipo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe responsável por representar a entidade Profissional.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "profissional")
public final class ProfissionalModelo extends AuditoriaModelo {
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "nascimento")
    private LocalDate nascimento;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "cargo", nullable = false)
    private CargoTipo cargoTipo;
    @OneToMany(mappedBy = "profissionalModelo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContatoModelo> contatoModeloLista;
}
