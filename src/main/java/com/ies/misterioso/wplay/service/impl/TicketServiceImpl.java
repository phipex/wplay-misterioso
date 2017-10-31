package com.ies.misterioso.wplay.service.impl;

import com.ies.misterioso.wplay.service.TicketService;
import com.ies.misterioso.wplay.domain.Ticket;
import com.ies.misterioso.wplay.repository.TicketRepository;
import com.ies.misterioso.wplay.service.dto.TicketDTO;
import com.ies.misterioso.wplay.service.mapper.TicketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Ticket.
 */
@Service
@Transactional
public class TicketServiceImpl implements TicketService{

    private final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TicketDTO save(TicketDTO ticketDTO) {
        log.debug("Request to save Ticket : {}", ticketDTO);
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
    }

    /**
     *  Get all the tickets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TicketDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tickets");
        return ticketRepository.findAll(pageable)
            .map(ticketMapper::toDto);
    }

    /**
     *  Get one ticket by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TicketDTO findOne(Long id) {
        log.debug("Request to get Ticket : {}", id);
        Ticket ticket = ticketRepository.findOne(id);
        return ticketMapper.toDto(ticket);
    }

    /**
     *  Delete the  ticket by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ticket : {}", id);
        ticketRepository.delete(id);
    }
}
