package com.ies.misterioso.wplay.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ies.misterioso.wplay.security.AuthoritiesConstants;
import com.ies.misterioso.wplay.service.MisteriosoService;
import com.ies.misterioso.wplay.web.rest.errors.BadRequestAlertException;
import com.ies.misterioso.wplay.web.rest.util.HeaderUtil;
import com.ies.misterioso.wplay.web.rest.util.PaginationUtil;
import com.ies.misterioso.wplay.service.dto.MisteriosoDTO;
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
 * REST controller for managing Misterioso.
 */
@RestController
@RequestMapping("/api")
public class MisteriosoResource {

    private final Logger log = LoggerFactory.getLogger(MisteriosoResource.class);

    private static final String ENTITY_NAME = "misterioso";

    private final MisteriosoService misteriosoService;

    public MisteriosoResource(MisteriosoService misteriosoService) {
        this.misteriosoService = misteriosoService;
    }

    /**
     * POST  /misteriosos : Create a new misterioso.
     *
     * @param misteriosoDTO the misteriosoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new misteriosoDTO, or with status 400 (Bad Request) if the misterioso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/misteriosos")
    @Timed
    @Secured({AuthoritiesConstants.COMERCIAL,AuthoritiesConstants.ADMIN})
    public ResponseEntity<MisteriosoDTO> createMisterioso(@Valid @RequestBody MisteriosoDTO misteriosoDTO) throws URISyntaxException {
        log.debug("REST request to save Misterioso : {}", misteriosoDTO);
        if (misteriosoDTO.getId() != null) {
            throw new BadRequestAlertException("A new misterioso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MisteriosoDTO result = misteriosoService.save(misteriosoDTO);
        return ResponseEntity.created(new URI("/api/misteriosos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /misteriosos : Updates an existing misterioso.
     *
     * @param misteriosoDTO the misteriosoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated misteriosoDTO,
     * or with status 400 (Bad Request) if the misteriosoDTO is not valid,
     * or with status 500 (Internal Server Error) if the misteriosoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/misteriosos")
    @Timed
    @Secured({AuthoritiesConstants.COMERCIAL,AuthoritiesConstants.ADMIN})
    public ResponseEntity<MisteriosoDTO> updateMisterioso(@Valid @RequestBody MisteriosoDTO misteriosoDTO) throws URISyntaxException {
        log.debug("REST request to update Misterioso : {}", misteriosoDTO);
        if (misteriosoDTO.getId() == null) {
            return createMisterioso(misteriosoDTO);
        }
        MisteriosoDTO result = misteriosoService.save(misteriosoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, misteriosoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /misteriosos : get all the misteriosos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of misteriosos in body
     */
    @GetMapping("/misteriosos")
    @Timed
    public ResponseEntity<List<MisteriosoDTO>> getAllMisteriosos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Misteriosos");
        Page<MisteriosoDTO> page = misteriosoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/misteriosos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /misteriosos/:id : get the "id" misterioso.
     *
     * @param id the id of the misteriosoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the misteriosoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/misteriosos/{id}")
    @Timed
    public ResponseEntity<MisteriosoDTO> getMisterioso(@PathVariable Long id) {
        log.debug("REST request to get Misterioso : {}", id);
        MisteriosoDTO misteriosoDTO = misteriosoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(misteriosoDTO));
    }

    /**
     * DELETE  /misteriosos/:id : delete the "id" misterioso.
     *
     * @param id the id of the misteriosoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/misteriosos/{id}")
    @Timed
    @Secured({AuthoritiesConstants.COMERCIAL,AuthoritiesConstants.ADMIN})
    public ResponseEntity<Void> deleteMisterioso(@PathVariable Long id) {
        log.debug("REST request to delete Misterioso : {}", id);
        misteriosoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
