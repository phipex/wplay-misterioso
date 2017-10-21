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
	
	ArrayList<MisteriosoDTO> listaMisteriososActualizados;

	/**
	 * @param ticketDTO
	 * @param ganadorDTO
	 * @param listaMisteriososActualizados
	 */
	public RetornoTicketDTO(TicketDTO ticketDTO, TicketGanadorDTO ganadorDTO,
			List<MisteriosoDTO> listaMisteriososActualizados) {
		this.ticketDTO = ticketDTO;
		this.ganadorDTO = ganadorDTO;
		this.listaMisteriososActualizados = (ArrayList<MisteriosoDTO>) listaMisteriososActualizados;
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

	public List<MisteriosoDTO> getListaMisteriososActualizados() {
		return listaMisteriososActualizados;
	}

	public void setListaMisteriososActualizados(ArrayList<MisteriosoDTO> listaMisteriososActualizados) {
		this.listaMisteriososActualizados = listaMisteriososActualizados;
	} 

	public void setListaMisteriososActualizados(List<MisteriosoDTO> listaMisteriososActualizados) {
		this.listaMisteriososActualizados = (ArrayList<MisteriosoDTO>) listaMisteriososActualizados;
	} 

	
}
