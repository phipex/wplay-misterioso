package com.ies.misterioso.wplay.service.impl;

import com.ies.misterioso.wplay.service.TicketGanadorService;
import com.ies.misterioso.wplay.domain.TicketGanador;
import com.ies.misterioso.wplay.repository.TicketGanadorRepository;
import com.ies.misterioso.wplay.service.dto.TicketGanadorDTO;
import com.ies.misterioso.wplay.service.mapper.TicketGanadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TicketGanador.
 */
@Service
@Transactional
public class TicketGanadorServiceImpl implements TicketGanadorService{

    private final Logger log = LoggerFactory.getLogger(TicketGanadorServiceImpl.class);

    private final TicketGanadorRepository ticketGanadorRepository;

    private final TicketGanadorMapper ticketGanadorMapper;

    public TicketGanadorServiceImpl(TicketGanadorRepository ticketGanadorRepository, TicketGanadorMapper ticketGanadorMapper) {
        this.ticketGanadorRepository = ticketGanadorRepository;
        this.ticketGanadorMapper = ticketGanadorMapper;
    }

    /**
     * Save a ticketGanador.
     *
     * @param ticketGanadorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TicketGanadorDTO save(TicketGanadorDTO ticketGanadorDTO) {
        log.debug("Request to save TicketGanador : {}", ticketGanadorDTO);
        TicketGanador ticketGanador = ticketGanadorMapper.toEntity(ticketGanadorDTO);
        ticketGanador = ticketGanadorRepository.save(ticketGanador);
        return ticketGanadorMapper.toDto(ticketGanador);
    }

    @Override
	public TicketGanadorDTO save(TicketGanador ticketGanador) {
    	ticketGanador = ticketGanadorRepository.save(ticketGanador);
        return ticketGanadorMapper.toDto(ticketGanador);
	}
    
    /**
     *  Get all the ticketGanadors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TicketGanadorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketGanadors");
        return ticketGanadorRepository.findAll(pageable)
            .map(ticketGanadorMapper::toDto);
    }

    /**
     *  Get one ticketGanador by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TicketGanadorDTO findOne(Long id) {
        log.debug("Request to get TicketGanador : {}", id);
        TicketGanador ticketGanador = ticketGanadorRepository.findOne(id);
        return ticketGanadorMapper.toDto(ticketGanador);
    }

    /**
     *  Delete the  ticketGanador by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketGanador : {}", id);
        ticketGanadorRepository.delete(id);
    }

	
}
