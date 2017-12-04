package com.ies.misterioso.wplay.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class RetornoTicketDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1853051945157771826L;

	TicketDTO ticketDTO;
	
	TicketGanadorDTO ganadorDTO;
	
	ArrayList<MisteriosoTicketDTO> listaMisteriososActualizados;

	/**
	 * @param ticketDTO
	 * @param ganadorDTO
	 * @param listaMisteriososActualizados
	 */
	public RetornoTicketDTO(TicketDTO ticketDTO, TicketGanadorDTO ganadorDTO,
			List<MisteriosoTicketDTO> listaMisteriososActualizados) {
		this.ticketDTO = ticketDTO;
		this.ganadorDTO = ganadorDTO;
		this.listaMisteriososActualizados = (ArrayList<MisteriosoTicketDTO>) listaMisteriososActualizados;
	}

	/**
	 * 
	 */
	public RetornoTicketDTO() {
	}

	public TicketDTO getTicketDTO() {
		return ticketDTO;
	}

	public void setTicketDTO(TicketDTO ticketDTO) {
		this.ticketDTO = ticketDTO;
	}

	public TicketGanadorDTO getGanadorDTO() {
		return ganadorDTO;
	}

	public void setGanadorDTO(TicketGanadorDTO ganadorDTO) {
		this.ganadorDTO = ganadorDTO;
	}

	public List<MisteriosoTicketDTO> getListaMisteriososActualizados() {
		return listaMisteriososActualizados;
	}

	/*public void setListaMisteriososActualizados(ArrayList<MisteriosoDTO> listaMisteriososActualizados) {
		this.listaMisteriososActualizados = listaMisteriososActualizados;
	}*/ 

	public void setListaMisteriososActualizados(List<MisteriosoTicketDTO> listaMisteriososActualizados) {
		this.listaMisteriososActualizados = (ArrayList<MisteriosoTicketDTO>) listaMisteriososActualizados;
	}

	@Override
	public String toString() {
		return "RetornoTicketDTO [ticketDTO=" + ticketDTO + ", ganadorDTO=" + ganadorDTO
				+ ", listaMisteriososActualizados=" + listaMisteriososActualizados + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ganadorDTO == null) ? 0 : ganadorDTO.hashCode());
		result = prime * result
				+ ((listaMisteriososActualizados == null) ? 0 : listaMisteriososActualizados.hashCode());
		result = prime * result + ((ticketDTO == null) ? 0 : ticketDTO.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetornoTicketDTO other = (RetornoTicketDTO) obj;
		if (ganadorDTO == null) {
			if (other.ganadorDTO != null)
				return false;
		} else if (!ganadorDTO.equals(other.ganadorDTO))
			return false;
		if (listaMisteriososActualizados == null) {
			if (other.listaMisteriososActualizados != null)
				return false;
		} else if (!listaMisteriososActualizados.equals(other.listaMisteriososActualizados))
			return false;
		if (ticketDTO == null) {
			if (other.ticketDTO != null)
				return false;
		} else if (!ticketDTO.equals(other.ticketDTO))
			return false;
		return true;
	} 
	
}
