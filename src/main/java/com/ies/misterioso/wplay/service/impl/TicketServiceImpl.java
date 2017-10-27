package com.ies.misterioso.wplay.service.impl;

import com.ies.misterioso.wplay.service.MisteriosoService;
import com.ies.misterioso.wplay.service.TicketGanadorService;
import com.ies.misterioso.wplay.service.TicketService;
import com.ies.misterioso.wplay.domain.Ticket;
import com.ies.misterioso.wplay.repository.TicketRepository;
import com.ies.misterioso.wplay.service.dto.MisteriosoDTO;
import com.ies.misterioso.wplay.service.dto.RetornoTicketDTO;
import com.ies.misterioso.wplay.service.dto.TicketDTO;
import com.ies.misterioso.wplay.service.dto.TicketGanadorDTO;
import com.ies.misterioso.wplay.service.mapper.TicketMapper;

import java.time.ZonedDateTime;
import java.util.List;

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
    
    private final MisteriosoService misteriosoService;
    
    private final TicketGanadorService ticketGanadorService;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, MisteriosoService misteriosoService, TicketGanadorService ticketGanadorService) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.misteriosoService = misteriosoService;
        this.ticketGanadorService = ticketGanadorService;
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

    
    @Override
    public RetornoTicketDTO nuevoTicket(TicketDTO ticketDTO) {
    	
    	log.debug("Request to new Ticket : {}", ticketDTO);
        
    	ticketDTO.setFecha(ZonedDateTime.now());
    	ticketDTO.setParticipa_misterioso("");
    	
    	Ticket ticket = ticketMapper.toEntity(ticketDTO);
        
        //ticket = ticketRepository.save(ticket);
        final RetornoTicketDTO retornaTicket = misteriosoService.verificaGanador(ticket);
        ticket = ticketRepository.save(ticket);
        
        TicketGanadorDTO ganadorDTO = retornaTicket.getGanadorDTO();
		if(ganadorDTO != null) {
			ganadorDTO.setTicketId(ticket.getId());
        	//TODO guardar ganador
			ganadorDTO = ticketGanadorService.save(ganadorDTO);//TODO colocarlo en ticket
        }
        
		/*final List<MisteriosoDTO> listaMisteriososActualizados = retornaTicket.getListaMisteriososActualizados();
		
		for (MisteriosoDTO misteriosoDTO : listaMisteriososActualizados) {
			//TODO guardar misteriosos
			misteriosoService.save(misteriosoDTO);
			
		}
		*/
        
        log.debug("ticket con la nueva informacion : {}", ticket);
        
        retornaTicket.setTicketDTO(ticketMapper.toDto(ticket));
        
        log.debug("resultado del servicio {}",retornaTicket);
        
        return retornaTicket;
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