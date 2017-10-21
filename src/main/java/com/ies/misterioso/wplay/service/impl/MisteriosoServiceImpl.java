package com.ies.misterioso.wplay.service.impl;

import com.ies.misterioso.wplay.service.MisteriosoService;
import com.ies.misterioso.wplay.domain.Misterioso;
import com.ies.misterioso.wplay.domain.Ticket;
import com.ies.misterioso.wplay.domain.TicketGanador;
import com.ies.misterioso.wplay.domain.enumeration.EstadoGanador;
import com.ies.misterioso.wplay.repository.MisteriosoRepository;
import com.ies.misterioso.wplay.service.dto.MisteriosoDTO;
import com.ies.misterioso.wplay.service.dto.RetornoTicketDTO;
import com.ies.misterioso.wplay.service.mapper.MisteriosoMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Misterioso.
 */
@Service
@Transactional
public class MisteriosoServiceImpl implements MisteriosoService{

    private final Logger log = LoggerFactory.getLogger(MisteriosoServiceImpl.class);

    private final MisteriosoRepository misteriosoRepository;

    private final MisteriosoMapper misteriosoMapper;

    public MisteriosoServiceImpl(MisteriosoRepository misteriosoRepository, MisteriosoMapper misteriosoMapper) {
        this.misteriosoRepository = misteriosoRepository;
        this.misteriosoMapper = misteriosoMapper;
    }

    /**
     * Save a misterioso.
     *
     * @param misteriosoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MisteriosoDTO save(MisteriosoDTO misteriosoDTO) {
        log.debug("Request to save Misterioso : {}", misteriosoDTO);
        Misterioso misterioso = misteriosoMapper.toEntity(misteriosoDTO);
        misterioso = misteriosoRepository.save(misterioso);
        return misteriosoMapper.toDto(misterioso);
    }

    /**
     *  Get all the misteriosos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MisteriosoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Misteriosos");
        return misteriosoRepository.findAll(pageable)
            .map(misteriosoMapper::toDto);
    }

    /**
     *  Get one misterioso by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MisteriosoDTO findOne(Long id) {
        log.debug("Request to get Misterioso : {}", id);
        Misterioso misterioso = misteriosoRepository.findOne(id);
        return misteriosoMapper.toDto(misterioso);
    }

    /**
     *  Delete the  misterioso by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Misterioso : {}", id);
        misteriosoRepository.delete(id);
    }

    
    public List<MisteriosoDTO> getAllMisterioso(){
    	
    	List<MisteriosoDTO> all = new ArrayList<>();
    	
    	List<Misterioso> getAll = misteriosoRepository.findAll();
    	
    	if(getAll.isEmpty()) {
    		return all;
    	}
    	
    	for (Misterioso misterioso : getAll) {
    		final MisteriosoDTO dto = misteriosoMapper.toDto(misterioso);
    		all.add(dto);
		}
    	
    	return all;
    }
    
    
    public RetornoTicketDTO verificaGanador(Ticket ticket) {
    	
    	RetornoTicketDTO retornoGanador = new RetornoTicketDTO();
    	
    	
    	
    	//traer los misteriosos que participan
    	final Integer cantidadApuestas = ticket.getCantidad_apuestas();
    	
    	final List<Misterioso> misteriososParticipa = findMisteriosoParticipa(cantidadApuestas);
    	
    	//para cada misterioso en que participa
    	for (Misterioso misterioso : misteriososParticipa) {
    		// actualizar el ticket con la info de los misteriosos que participa
        	String misteriosoParticipa = ticket.getParticipa_misterioso();
    		misteriosoParticipa = misteriosoParticipa + Long.toString(misterioso.getId());
    		
    		ticket.setParticipa_misterioso(misteriosoParticipa);
    		
    		// aumentar el contador de tickets
    		int cantidadTicket = misterioso.getCantidad_apuestas() + 1;
    		    		
        	//verificar si es ganador
    		boolean isGanador = isGanador(misterioso, cantidadTicket);
    		
    		if(isGanador) {//si es ganador
    			//TODO crear el registro de ganador
    			//TODO reinicia los valores del misterioso
    			
    		}else{// si no es ganador
    			
    			//aumentar el contador de tickets
    			misterioso.setCantidad_apuestas(cantidadTicket);
    			
    			//aumentar el acumulado de misterioso
    			BigDecimal cantidadActual = misterioso.getAcumulado();
    			
    			BigDecimal valorApuestaTicket = ticket.getValor_total();
    			
    			final Float porcentajeTicket = misterioso.getPorcentaje_ticket();
    			
    			BigDecimal cantidadASumar = percentage(valorApuestaTicket, new BigDecimal(porcentajeTicket));
    			
    			//TODO si no es mayor que el limite agregar al acumulado o lo que le falte
    			
    			final BigDecimal maximoAcumulado = misterioso.getValor_base_max();
    			
    			if(maximoAcumulado.compareTo(cantidadASumar) ) {
    				
    			}
    			
    			final BigDecimal nuevoAcumulado = cantidadActual.add(cantidadASumar);
    			
    			misterioso.setAcumulado(nuevoAcumulado);
    			
    			misteriosoRepository.save(misterioso);
    			
    		}
		}
    	
    	
    	//final List<MisteriosoDTO> allMisteriosos = getAllMisterioso();
    	
    	
    	
    	//retornoGanador.setListaMisteriososActualizados(listaMisteriososActualizados);
    	
    	
    	
    	//retornoGanador.setTicketDTO(ticketDTO);//TODO hay que importar el mapper o hacer algo en el dto
    	
    	
    	return retornoGanador;
    }    
    
	@Override
	public List<Misterioso> findMisteriosoParticipa(int cantidadApuestas) {
		
		String scantidadApuestas = Integer.toString(cantidadApuestas);
		
		return misteriosoRepository.findByCantidadApuestasMax(scantidadApuestas);
	}
    
	/**
	 * @deprecated el metodo debe codificar el numero de ticket ganador
	 * @param misterioso
	 * @param cantidadTicket
	 * @return
	 */
    private boolean isGanador(Misterioso misterioso, int cantidadTicket) {
    	
    	int ticketGanador = Integer.valueOf(misterioso.getGanador()); 
    	
    	return ticketGanador == cantidadTicket;
    }
    
    /**
     * @deprecated TODO hay que pasar a un archivo utils
     * @param base
     * @param pct
     * @return
     */
    public static BigDecimal percentage(BigDecimal base, BigDecimal pct){
        return base.multiply(pct).divide(new BigDecimal(100));
    }
    
}
