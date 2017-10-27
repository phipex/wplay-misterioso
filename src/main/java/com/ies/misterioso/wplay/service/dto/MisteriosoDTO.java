package com.ies.misterioso.wplay.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.ies.misterioso.wplay.domain.enumeration.EstadoMisterioso;

/**
 * A DTO for the Misterioso entity.
 */
public class MisteriosoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    private BigDecimal acumulado;

    @NotNull
    @Min(value = 0)
    private Integer cantidad_apuestas;

    @NotNull
    private Float porcentaje_ticket;

    @NotNull
    private BigDecimal valor_base_min;

    @NotNull
    @DecimalMax(value = "100000000")
    private BigDecimal valor_base_max;

    @NotNull
    private Integer minimo_ticket;

    @NotNull
    private Integer maximo_ticket;

    private String ganador;

    @NotNull
    private EstadoMisterioso estado;

    @Size(max = 1024)
    private String descripcion;

    @NotNull
    private Integer cantidad_apuestas_minima;

    @NotNull
    private Integer cantidad_apuestas_maxima;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(BigDecimal acumulado) {
        this.acumulado = acumulado;
    }

    public Integer getCantidad_apuestas() {
        return cantidad_apuestas;
    }

    public void setCantidad_apuestas(Integer cantidad_apuestas) {
        this.cantidad_apuestas = cantidad_apuestas;
    }

    public Float getPorcentaje_ticket() {
        return porcentaje_ticket;
    }

    public void setPorcentaje_ticket(Float porcentaje_ticket) {
        this.porcentaje_ticket = porcentaje_ticket;
    }

    public BigDecimal getValor_base_min() {
        return valor_base_min;
    }

    public void setValor_base_min(BigDecimal valor_base_min) {
        this.valor_base_min = valor_base_min;
    }

    public BigDecimal getValor_base_max() {
        return valor_base_max;
    }

    public void setValor_base_max(BigDecimal valor_base_max) {
        this.valor_base_max = valor_base_max;
    }

    public Integer getMinimo_ticket() {
        return minimo_ticket;
    }

    public void setMinimo_ticket(Integer minimo_ticket) {
        this.minimo_ticket = minimo_ticket;
    }

    public Integer getMaximo_ticket() {
        return maximo_ticket;
    }

    public void setMaximo_ticket(Integer maximo_ticket) {
        this.maximo_ticket = maximo_ticket;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public EstadoMisterioso getEstado() {
        return estado;
    }

    public void setEstado(EstadoMisterioso estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad_apuestas_minima() {
        return cantidad_apuestas_minima;
    }

    public void setCantidad_apuestas_minima(Integer cantidad_apuestas_minima) {
        this.cantidad_apuestas_minima = cantidad_apuestas_minima;
    }

    public Integer getCantidad_apuestas_maxima() {
        return cantidad_apuestas_maxima;
    }

    public void setCantidad_apuestas_maxima(Integer cantidad_apuestas_maxima) {
        this.cantidad_apuestas_maxima = cantidad_apuestas_maxima;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MisteriosoDTO misteriosoDTO = (MisteriosoDTO) o;
        if(misteriosoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), misteriosoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MisteriosoDTO{" +
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
