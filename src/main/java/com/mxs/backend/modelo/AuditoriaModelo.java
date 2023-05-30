package com.mxs.backend.modelo;

import com.mxs.backend.tipo.StatusTipo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe responsável por representar os campos de auditoria que serão mapeados entre as entidades do módulo.
 */
@Getter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public sealed class AuditoriaModelo permits ProfissionalModelo, ContatoModelo {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;
    @Column(name = "codigo", nullable = false, updatable = false, length = 36)
    private String codigo;
    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dataCriacao;
    @Column(name = "data_ultima_atualizacao", nullable = false)
    @LastModifiedDate
    private LocalDateTime dataUltimaAtualizacao;
    @Column(name = "status", nullable = false, length = 1)
    private String status;
    @Transient
    private StatusTipo statusTipo;

    @PrePersist
    public void preencherAntesDeSalvar() {
        if (codigo == null) {
            UUID uuid = UUID.randomUUID();
            codigo = uuid.toString();
        }

        if (statusTipo == null) {
            status = StatusTipo.ATIVO.getCodigo();
        } else {
            status = statusTipo.getCodigo();
        }
    }

    @PostLoad
    public void preencherStatusTipo() {
        if (!status.isBlank()) {
            statusTipo = StatusTipo.of(status);
        }
    }

    private void setStatusType(StatusTipo statusTipo) {
        this.statusTipo = statusTipo;
        status = statusTipo.getCodigo();
    }

    public void inativarRegistro() {
        setStatusType(StatusTipo.INATIVO);
    }
}
