package com.ies.misterioso.wplay.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Ticket entity.
 */
public class TicketDTO implements Serializable {

    private Long id;

    @NotNull
    private String id_fuente;

    @NotNull
    private Integer cantidad_apuestas;

    @NotNull
    @DecimalMax(value = "100000000")
    private BigDecimal valor_total;

    @NotNull
    private ZonedDateTime fecha;

    @NotNull
    private String participa_misterioso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_fuente() {
        return id_fuente;
    }

    public void setId_fuente(String id_fuente) {
        this.id_fuente = id_fuente;
    }

    public Integer getCantidad_apuestas() {
        return cantidad_apuestas;
    }

    public void setCantidad_apuestas(Integer cantidad_apuestas) {
        this.cantidad_apuestas = cantidad_apuestas;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getParticipa_misterioso() {
        return participa_misterioso;
    }

    public void setParticipa_misterioso(String participa_misterioso) {
        this.participa_misterioso = participa_misterioso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;
        if(ticketDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
            "id=" + getId() +
            ", id_fuente='" + getId_fuente() + "'" +
            ", cantidad_apuestas='" + getCantidad_apuestas() + "'" +
            ", valor_total='" + getValor_total() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", participa_misterioso='" + getParticipa_misterioso() + "'" +
            "}";
    }
}
