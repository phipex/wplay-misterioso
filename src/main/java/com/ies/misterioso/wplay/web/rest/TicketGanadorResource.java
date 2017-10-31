package com.ies.misterioso.wplay.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ies.misterioso.wplay.service.TicketGanadorService;
import com.ies.misterioso.wplay.web.rest.util.HeaderUtil;
import com.ies.misterioso.wplay.web.rest.util.PaginationUtil;
import com.ies.misterioso.wplay.service.dto.TicketGanadorDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TicketGanador.
 */
@RestController
@RequestMapping("/api")
public class TicketGanadorResource {

    private final Logger log = LoggerFactory.getLogger(TicketGanadorResource.class);

    private static final String ENTITY_NAME = "ticketGanador";

    private final TicketGanadorService ticketGanadorService;

    public TicketGanadorResource(TicketGanadorService ticketGanadorService) {
        this.ticketGanadorService = ticketGanadorService;
    }

    /**
     * POST  /ticket-ganadors : Create a new ticketGanador.
     *
     * @param ticketGanadorDTO the ticketGanadorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticketGanadorDTO, or with status 400 (Bad Request) if the ticketGanador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ticket-ganadors")
    @Timed
    public ResponseEntity<TicketGanadorDTO> createTicketGanador(@Valid @RequestBody TicketGanadorDTO ticketGanadorDTO) throws URISyntaxException {
        log.debug("REST request to save TicketGanador : {}", ticketGanadorDTO);
        if (ticketGanadorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ticketGanador cannot already have an ID")).body(null);
        }
        TicketGanadorDTO result = ticketGanadorService.save(ticketGanadorDTO);
        return ResponseEntity.created(new URI("/api/ticket-ganadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ticket-ganadors : Updates an existing ticketGanador.
     *
     * @param ticketGanadorDTO the ticketGanadorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticketGanadorDTO,
     * or with status 400 (Bad Request) if the ticketGanadorDTO is not valid,
     * or with status 500 (Internal Server Error) if the ticketGanadorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ticket-ganadors")
    @Timed
    public ResponseEntity<TicketGanadorDTO> updateTicketGanador(@Valid @RequestBody TicketGanadorDTO ticketGanadorDTO) throws URISyntaxException {
        log.debug("REST request to update TicketGanador : {}", ticketGanadorDTO);
        if (ticketGanadorDTO.getId() == null) {
            return createTicketGanador(ticketGanadorDTO);
        }
        TicketGanadorDTO result = ticketGanadorService.save(ticketGanadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ticketGanadorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ticket-ganadors : get all the ticketGanadors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ticketGanadors in body
     */
    @GetMapping("/ticket-ganadors")
    @Timed
    public ResponseEntity<List<TicketGanadorDTO>> getAllTicketGanadors(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TicketGanadors");
        Page<TicketGanadorDTO> page = ticketGanadorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ticket-ganadors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ticket-ganadors/:id : get the "id" ticketGanador.
     *
     * @param id the id of the ticketGanadorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticketGanadorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ticket-ganadors/{id}")
    @Timed
    public ResponseEntity<TicketGanadorDTO> getTicketGanador(@PathVariable Long id) {
        log.debug("REST request to get TicketGanador : {}", id);
        TicketGanadorDTO ticketGanadorDTO = ticketGanadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ticketGanadorDTO));
    }

    /**
     * DELETE  /ticket-ganadors/:id : delete the "id" ticketGanador.
     *
     * @param id the id of the ticketGanadorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ticket-ganadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicketGanador(@PathVariable Long id) {
        log.debug("REST request to delete TicketGanador : {}", id);
        ticketGanadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
