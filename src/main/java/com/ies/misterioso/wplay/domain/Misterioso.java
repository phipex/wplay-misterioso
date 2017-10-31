package com.ies.misterioso.wplay.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.ies.misterioso.wplay.domain.enumeration.EstadoMisterioso;

/**
 * A Misterioso.
 */
@Entity
@Table(name = "misterioso")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Misterioso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    @Column(name = "acumulado", precision=10, scale=2)
    private BigDecimal acumulado;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad_apuestas", nullable = false)
    private Integer cantidad_apuestas;

    @NotNull
    @Column(name = "porcentaje_ticket", nullable = false)
    private Float porcentaje_ticket;

    @NotNull
    @Column(name = "valor_base_min", precision=10, scale=2, nullable = false)
    private BigDecimal valor_base_min;

    @NotNull
    @DecimalMax(value = "100000000")
    @Column(name = "valor_base_max", precision=10, scale=2, nullable = false)
    private BigDecimal valor_base_max;

    @NotNull
    @Column(name = "minimo_ticket", nullable = false)
    private Integer minimo_ticket;

    @NotNull
    @Column(name = "maximo_ticket", nullable = false)
    private Integer maximo_ticket;

    @Column(name = "ganador")
    private String ganador;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMisterioso estado;

    @Size(max = 1024)
    @Column(name = "descripcion", length = 1024)
    private String descripcion;

    @NotNull
    @Column(name = "cantidad_apuestas_minima", nullable = false)
    private Integer cantidad_apuestas_minima;

    @NotNull
    @Column(name = "cantidad_apuestas_maxima", nullable = false)
    private Integer cantidad_apuestas_maxima;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Misterioso nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getAcumulado() {
        return acumulado;
    }

    public Misterioso acumulado(BigDecimal acumulado) {
        this.acumulado = acumulado;
        return this;
    }

    public void setAcumulado(BigDecimal acumulado) {
        this.acumulado = acumulado;
    }

    public Integer getCantidad_apuestas() {
        return cantidad_apuestas;
    }

    public Misterioso cantidad_apuestas(Integer cantidad_apuestas) {
        this.cantidad_apuestas = cantidad_apuestas;
        return this;
    }

    public void setCantidad_apuestas(Integer cantidad_apuestas) {
        this.cantidad_apuestas = cantidad_apuestas;
    }

    public Float getPorcentaje_ticket() {
        return porcentaje_ticket;
    }

    public Misterioso porcentaje_ticket(Float porcentaje_ticket) {
        this.porcentaje_ticket = porcentaje_ticket;
        return this;
    }

    public void setPorcentaje_ticket(Float porcentaje_ticket) {
        this.porcentaje_ticket = porcentaje_ticket;
    }

    public BigDecimal getValor_base_min() {
        return valor_base_min;
    }

    public Misterioso valor_base_min(BigDecimal valor_base_min) {
        this.valor_base_min = valor_base_min;
        return this;
    }

    public void setValor_base_min(BigDecimal valor_base_min) {
        this.valor_base_min = valor_base_min;
    }

    public BigDecimal getValor_base_max() {
        return valor_base_max;
    }

    public Misterioso valor_base_max(BigDecimal valor_base_max) {
        this.valor_base_max = valor_base_max;
        return this;
    }

    public void setValor_base_max(BigDecimal valor_base_max) {
        this.valor_base_max = valor_base_max;
    }

    public Integer getMinimo_ticket() {
        return minimo_ticket;
    }

    public Misterioso minimo_ticket(Integer minimo_ticket) {
        this.minimo_ticket = minimo_ticket;
        return this;
    }

    public void setMinimo_ticket(Integer minimo_ticket) {
        this.minimo_ticket = minimo_ticket;
    }

    public Integer getMaximo_ticket() {
        return maximo_ticket;
    }

    public Misterioso maximo_ticket(Integer maximo_ticket) {
        this.maximo_ticket = maximo_ticket;
        return this;
    }

    public void setMaximo_ticket(Integer maximo_ticket) {
        this.maximo_ticket = maximo_ticket;
    }

    public String getGanador() {
        return ganador;
    }

    public Misterioso ganador(String ganador) {
        this.ganador = ganador;
        return this;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public EstadoMisterioso getEstado() {
        return estado;
    }

    public Misterioso estado(EstadoMisterioso estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoMisterioso estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Misterioso descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad_apuestas_minima() {
        return cantidad_apuestas_minima;
    }

    public Misterioso cantidad_apuestas_minima(Integer cantidad_apuestas_minima) {
        this.cantidad_apuestas_minima = cantidad_apuestas_minima;
        return this;
    }

    public void setCantidad_apuestas_minima(Integer cantidad_apuestas_minima) {
        this.cantidad_apuestas_minima = cantidad_apuestas_minima;
    }

    public Integer getCantidad_apuestas_maxima() {
        return cantidad_apuestas_maxima;
    }

    public Misterioso cantidad_apuestas_maxima(Integer cantidad_apuestas_maxima) {
        this.cantidad_apuestas_maxima = cantidad_apuestas_maxima;
        return this;
    }

    public void setCantidad_apuestas_maxima(Integer cantidad_apuestas_maxima) {
        this.cantidad_apuestas_maxima = cantidad_apuestas_maxima;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Misterioso misterioso = (Misterioso) o;
        if (misterioso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), misterioso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Misterioso{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", acumulado='" + getAcumulado() + "'" +
            ", cantidad_apuestas='" + getCantidad_apuestas() + "'" +
            ", porcentaje_ticket='" + getPorcentaje_ticket() + "'" +
            ", valor_base_min='" + getValor_base_min() + "'" +
            ", valor_base_max='" + getValor_base_max() + "'" +
            ", minimo_ticket='" + getMinimo_ticket() + "'" +
            ", maximo_ticket='" + getMaximo_ticket() + "'" +
            ", ganador='" + getGanador() + "'" +
            ", estado='" + getEstado() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", cantidad_apuestas_minima='" + getCantidad_apuestas_minima() + "'" +
            ", cantidad_apuestas_maxima='" + getCantidad_apuestas_maxima() + "'" +
            "}";
    }
}
