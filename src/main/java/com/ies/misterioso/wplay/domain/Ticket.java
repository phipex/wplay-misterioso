package com.ies.misterioso.wplay.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_fuente", nullable = false)
    private String id_fuente;

    @NotNull
    @Column(name = "cantidad_apuestas", nullable = false)
    private Integer cantidad_apuestas;

    @NotNull
    @DecimalMax(value = "100000000")
    @Column(name = "valor_total", precision=10, scale=2, nullable = false)
    private BigDecimal valor_total;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private ZonedDateTime fecha;

    @NotNull
    @Column(name = "participa_misterioso", nullable = false)
    private String participa_misterioso;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_fuente() {
        return id_fuente;
    }

    public Ticket id_fuente(String id_fuente) {
        this.id_fuente = id_fuente;
        return this;
    }

    public void setId_fuente(String id_fuente) {
        this.id_fuente = id_fuente;
    }

    public Integer getCantidad_apuestas() {
        return cantidad_apuestas;
    }

    public Ticket cantidad_apuestas(Integer cantidad_apuestas) {
        this.cantidad_apuestas = cantidad_apuestas;
        return this;
    }

    public void setCantidad_apuestas(Integer cantidad_apuestas) {
        this.cantidad_apuestas = cantidad_apuestas;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public Ticket valor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
        return this;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Ticket fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getParticipa_misterioso() {
        return participa_misterioso;
    }

    public Ticket participa_misterioso(String participa_misterioso) {
        this.participa_misterioso = participa_misterioso;
        return this;
    }

    public void setParticipa_misterioso(String participa_misterioso) {
        this.participa_misterioso = participa_misterioso;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        if (ticket.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", id_fuente='" + getId_fuente() + "'" +
            ", cantidad_apuestas='" + getCantidad_apuestas() + "'" +
            ", valor_total='" + getValor_total() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", participa_misterioso='" + getParticipa_misterioso() + "'" +
            "}";
    }
}
