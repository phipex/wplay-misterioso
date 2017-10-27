package com.ies.misterioso.wplay.service;

import com.ies.misterioso.wplay.service.dto.RetornoTicketDTO;
import com.ies.misterioso.wplay.service.dto.TicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Ticket.
 */
public interface TicketService {

    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save
     * @return the persisted entity
     */
    TicketDTO save(TicketDTO ticketDTO);

    /**
     *  Get all the tickets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TicketDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" ticket.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TicketDTO findOne(Long id);

    /**
     *  Delete the "id" ticket.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

	RetornoTicketDTO nuevoTicket(TicketDTO ticketDTO);
}
