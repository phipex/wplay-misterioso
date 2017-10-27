package com.ies.misterioso.wplay.service;

import com.ies.misterioso.wplay.domain.TicketGanador;
import com.ies.misterioso.wplay.service.dto.TicketGanadorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TicketGanador.
 */
public interface TicketGanadorService {

    /**
     * Save a ticketGanador.
     *
     * @param ticketGanadorDTO the entity to save
     * @return the persisted entity
     */
    TicketGanadorDTO save(TicketGanadorDTO ticketGanadorDTO);

    /**
     *  Get all the ticketGanadors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TicketGanadorDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" ticketGanador.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TicketGanadorDTO findOne(Long id);

    /**
     *  Delete the "id" ticketGanador.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

	TicketGanadorDTO save(TicketGanador ticketGanador);
}
