package com.ies.misterioso.wplay.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A DTO for the Misterioso entity.
 */
public class MisteriosoTicketDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @DecimalMin(value = "0")
    @DecimalMax(value = "100000000")
    private BigDecimal acumulado;

   
    @Size(max = 1024)
    private String descripcion;

    

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

   

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MisteriosoTicketDTO misteriosoDTO = (MisteriosoTicketDTO) o;
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
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
