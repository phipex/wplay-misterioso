package com.ies.misterioso.wplay.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.ies.misterioso.wplay.domain.Misterioso;
import com.ies.misterioso.wplay.domain.Ticket;
import com.ies.misterioso.wplay.domain.enumeration.EstadoGanador;

/**
 * A DTO for the TicketGanador entity.
 */
public class TicketGanadorDTO implements Serializable {

    private Long id;

    @NotNull
    private EstadoGanador estado;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    private BigDecimal valor_ganado;

    private ZonedDateTime fecha;

    @Size(max = 1024)
    private String descripcion;

    private Integer indice_ticket_misterioso;

    private Long misteriosoId;

    private Long ticketId;

    public TicketGanadorDTO() {
    	
    }
    
    public TicketGanadorDTO(EstadoGanador estado, BigDecimal valorGanado, String descripcion,
			Integer indiceTicketMisterioso, Misterioso misterioso, Ticket ticket) {
		this.id = 0L;
		this.fecha = ZonedDateTime.now();
		this.estado = estado;
		this.valor_ganado = valorGanado;
		this.descripcion = descripcion;
		this.indice_ticket_misterioso = indiceTicketMisterioso;
		this.misteriosoId = misterioso.getId();
		this.ticketId = ticket.getId();
	}
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoGanador getEstado() {
        return estado;
    }

    public void setEstado(EstadoGanador estado) {
        this.estado = estado;
    }

    public BigDecimal getValor_ganado() {
        return valor_ganado;
    }

    public void setValor_ganado(BigDecimal valor_ganado) {
        this.valor_ganado = valor_ganado;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIndice_ticket_misterioso() {
        return indice_ticket_misterioso;
    }

    public void setIndice_ticket_misterioso(Integer indice_ticket_misterioso) {
        this.indice_ticket_misterioso = indice_ticket_misterioso;
    }

    public Long getMisteriosoId() {
        return misteriosoId;
    }

    public void setMisteriosoId(Long misteriosoId) {
        this.misteriosoId = misteriosoId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketGanadorDTO ticketGanadorDTO = (TicketGanadorDTO) o;
        if(ticketGanadorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketGanadorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketGanadorDTO{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", valor_ganado='" + getValor_ganado() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", indice_ticket_misterioso='" + getIndice_ticket_misterioso() + "'" +
            "}";
    }
}
