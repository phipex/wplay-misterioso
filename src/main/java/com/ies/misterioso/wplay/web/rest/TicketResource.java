package com.ies.misterioso.wplay.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ies.misterioso.wplay.security.AuthoritiesConstants;
import com.ies.misterioso.wplay.service.TicketService;
import com.ies.misterioso.wplay.web.rest.errors.BadRequestAlertException;
import com.ies.misterioso.wplay.web.rest.util.HeaderUtil;
import com.ies.misterioso.wplay.web.rest.util.PaginationUtil;
import com.ies.misterioso.wplay.service.dto.RetornoTicketDTO;
import com.ies.misterioso.wplay.service.dto.TicketDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ticket.
 */
@RestController
@RequestMapping("/api")
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);

    private static final String ENTITY_NAME = "ticket";

    private final TicketService ticketService;

    public TicketResource(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * POST  /tickets : Create a new ticket.
     *
     * @param ticketDTO the ticketDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticketDTO, or with status 400 (Bad Request) if the ticket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *//*
    @PostMapping("/tickets")
    @Timed
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketDTO);
        if (ticketDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticket cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketDTO result = ticketService.save(ticketDTO);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
*/
    /**
     * POST  /tickets :analiza si el ticket es un ganador
     *
     * @param ticketDTO the ticketDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticketDTO, or with status 400 (Bad Request) if the ticket has already an ID
     * @throws URISyntaxException  if the Location URI syntax is incorrect
     */
    @PostMapping("/analizaticket")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<RetornoTicketDTO> analizaTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
	    log.debug("REST request to save Ticket : {}", ticketDTO);
	    if (ticketDTO.getId() != null) {
	        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ticket cannot already have an ID")).body(null);
	    }
	    RetornoTicketDTO result = ticketService.nuevoTicket(ticketDTO);
	    final Long idNewTicket = result.getTicketDTO().getId();

	    return ResponseEntity.created(new URI("/api/tickets/" + idNewTicket))
	        .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, idNewTicket.toString()))
	        .body(result);

    }


    /*
    *//**
     * PUT  /tickets : Updates an existing ticket.
     *
     * @param ticketDTO the ticketDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticketDTO,
     * or with status 400 (Bad Request) if the ticketDTO is not valid,
     * or with status 500 (Internal Server Error) if the ticketDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *//*
    @PutMapping("/tickets")
    @Timed
    public ResponseEntity<TicketDTO> updateTicket(@Valid @RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to update Ticket : {}", ticketDTO);
        if (ticketDTO.getId() == null) {
            return createTicket(ticketDTO);
        }
        TicketDTO result = ticketService.save(ticketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ticketDTO.getId().toString()))
            .body(result);
    }*/

    /**
     * GET  /tickets : get all the tickets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tickets in body
     */
    @GetMapping("/tickets")
    @Timed
    public ResponseEntity<List<TicketDTO>> getAllTickets(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Tickets");
        Page<TicketDTO> page = ticketService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tickets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tickets/:id : get the "id" ticket.
     *
     * @param id the id of the ticketDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticketDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tickets/{id}")
    @Timed
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        log.debug("REST request to get Ticket : {}", id);
        TicketDTO ticketDTO = ticketService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ticketDTO));
    }

   /* *//**
     * DELETE  /tickets/:id : delete the "id" ticket.
     *
     * @param id the id of the ticketDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     *//*
    @DeleteMapping("/tickets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.debug("REST request to delete Ticket : {}", id);
        ticketService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }*/
}
