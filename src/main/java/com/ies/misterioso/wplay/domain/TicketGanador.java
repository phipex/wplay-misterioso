package com.ies.misterioso.wplay.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.ies.misterioso.wplay.domain.enumeration.EstadoGanador;

/**
 * A TicketGanador.
 */
@Entity
@Table(name = "ticket_ganador")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TicketGanador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoGanador estado;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    @Column(name = "valor_ganado", precision=10, scale=2)
    private BigDecimal valor_ganado;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @Size(max = 1024)
    @Column(name = "descripcion", length = 1024)
    private String descripcion;

    @Column(name = "indice_ticket_misterioso")
    private Integer indice_ticket_misterioso;

    @ManyToOne(optional = false)
    @NotNull
    private Misterioso misterioso;

    @OneToOne
    @JoinColumn(unique = true)
    private Ticket ticket;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoGanador getEstado() {
        return estado;
    }

    public TicketGanador estado(EstadoGanador estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoGanador estado) {
        this.estado = estado;
    }

    public BigDecimal getValor_ganado() {
        return valor_ganado;
    }

    public TicketGanador valor_ganado(BigDecimal valor_ganado) {
        this.valor_ganado = valor_ganado;
        return this;
    }

    public void setValor_ganado(BigDecimal valor_ganado) {
        this.valor_ganado = valor_ganado;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public TicketGanador fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TicketGanador descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIndice_ticket_misterioso() {
        return indice_ticket_misterioso;
    }

    public TicketGanador indice_ticket_misterioso(Integer indice_ticket_misterioso) {
        this.indice_ticket_misterioso = indice_ticket_misterioso;
        return this;
    }

    public void setIndice_ticket_misterioso(Integer indice_ticket_misterioso) {
        this.indice_ticket_misterioso = indice_ticket_misterioso;
    }

    public Misterioso getMisterioso() {
        return misterioso;
    }

    public TicketGanador misterioso(Misterioso misterioso) {
        this.misterioso = misterioso;
        return this;
    }

    public void setMisterioso(Misterioso misterioso) {
        this.misterioso = misterioso;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public TicketGanador ticket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
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
        TicketGanador ticketGanador = (TicketGanador) o;
        if (ticketGanador.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ticketGanador.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TicketGanador{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            ", valor_ganado='" + getValor_ganado() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", indice_ticket_misterioso='" + getIndice_ticket_misterioso() + "'" +
            "}";
    }
}
