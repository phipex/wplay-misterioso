package com.ies.misterioso.wplay.service.impl;

import com.ies.misterioso.wplay.service.MisteriosoService;
import com.ies.misterioso.wplay.service.TicketGanadorService;
import com.ies.misterioso.wplay.domain.Misterioso;
import com.ies.misterioso.wplay.domain.Ticket;
import com.ies.misterioso.wplay.domain.TicketGanador;
import com.ies.misterioso.wplay.domain.enumeration.EstadoGanador;
import com.ies.misterioso.wplay.repository.MisteriosoRepository;
import com.ies.misterioso.wplay.service.dto.MisteriosoDTO;
import com.ies.misterioso.wplay.service.dto.RetornoTicketDTO;
import com.ies.misterioso.wplay.service.dto.TicketGanadorDTO;
import com.ies.misterioso.wplay.service.mapper.MisteriosoMapper;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.token.SecureRandomFactoryBean;
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

    private final TicketGanadorService ticketGanadorService;
    
    public MisteriosoServiceImpl(MisteriosoRepository misteriosoRepository, MisteriosoMapper misteriosoMapper, TicketGanadorService ticketGanadorService) {
        this.misteriosoRepository = misteriosoRepository;
        this.misteriosoMapper = misteriosoMapper;
        this.ticketGanadorService = ticketGanadorService;
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

    @Override
    @Transactional(readOnly = true)
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
    
    @Override
    public RetornoTicketDTO verificaGanador(Ticket ticket) {
    	
    	RetornoTicketDTO retornoGanador = new RetornoTicketDTO();
    	
    	TicketGanadorDTO ticketGanadorDto = null;
    	
    	//traer los misteriosos que participan
    	final Integer cantidadApuestas = ticket.getCantidad_apuestas();
    	
    	final List<Misterioso> misteriososParticipa = findMisteriosoParticipa(cantidadApuestas);
    	
    	//para cada misterioso en que participa
    	for (Misterioso misterioso : misteriososParticipa) {
    		// actualizar el ticket con la info de los misteriosos que participa
        	String misteriosoParticipa = ticket.getParticipa_misterioso();
    		misteriosoParticipa = misteriosoParticipa + Long.toString(misterioso.getId())+",";
    		
    		ticket.setParticipa_misterioso(misteriosoParticipa);
    		
    		// aumentar el contador de tickets
    		int cantidadTicket = misterioso.getCantidad_apuestas() + 1;
    		    		
        	//verificar si es ganador
    		boolean isGanador = isGanador(misterioso, cantidadTicket);
    		
    		if(isGanador) {//si es ganador
    			//crear el registro de ganador
    			EstadoGanador estado = EstadoGanador.GANADO; 
    			BigDecimal valorGanado = misterioso.getAcumulado();
    			String descripcion =  null;
    			Integer indiceTicketMisterioso = cantidadTicket;
    			
    			TicketGanador ticketGanador = new TicketGanador(estado, valorGanado, descripcion,
    					indiceTicketMisterioso, misterioso, ticket);
    			
    			ticketGanadorDto = ticketGanadorService.save(ticketGanador);
    			    			
    			//reinicia los valores del misterioso
    			reiniciaMisterioso(misterioso);
    			
    			misteriosoRepository.save(misterioso);
    			
    			break;
    			
    		}else{// si no es ganador
    			
    			//aumentar el contador de tickets
    			misterioso.setCantidad_apuestas(cantidadTicket);
    			
    			//aumentar el acumulado de misterioso
    			BigDecimal cantidadActual = misterioso.getAcumulado();
    			
    			BigDecimal valorApuestaTicket = ticket.getValor_total();
    			
    			final Float porcentajeTicket = misterioso.getPorcentaje_ticket();
    			
    			BigDecimal cantidadAporte = percentage(valorApuestaTicket, new BigDecimal(porcentajeTicket));
    			
    			BigDecimal futuroAcumulado = cantidadActual.add(cantidadAporte);
    			
    			final BigDecimal maximoAcumulado = misterioso.getValor_base_max();
    			
    			BigDecimal cantidadASumar = null;
    			
    			final int compareTo = maximoAcumulado.compareTo(futuroAcumulado);
				//si no es mayor que el limite agregar al acumulado o lo que le falte
    			final boolean superaAcumulado = compareTo < 0;
    			final boolean llegoLimite = compareTo == 0;
    			
				if(superaAcumulado ) {
    				cantidadASumar = maximoAcumulado.subtract(cantidadActual);
    				
    			}else if(llegoLimite) {
    				cantidadASumar = BigDecimal.ZERO;
    				
    			}else {
    				cantidadASumar = cantidadAporte;
    			}
    			
				
    			final BigDecimal nuevoAcumulado = cantidadActual.add(cantidadASumar);
    			
    			misterioso.setAcumulado(nuevoAcumulado);
    			
    			misteriosoRepository.save(misterioso);
    			
    		}
		}
    	
    	retornoGanador.setListaMisteriososActualizados(listEntityToListDTO(misteriososParticipa));
    	
    	retornoGanador.setGanadorDTO(ticketGanadorDto);
    	
    	
    	return retornoGanador;
    }    
    
    private void reiniciaMisterioso(Misterioso misterioso) {
    	
    	//reiniciar el acumulado
    	
    	BigDecimal nuevoAcumulado = misterioso.getValor_base_min();
    	
    	misterioso.setAcumulado(nuevoAcumulado);
    	
    	//reiniciar la cantidad de ticket
    	
    	misterioso.setCantidad_apuestas(0);
    	
    	//crear nuevo ganador aleatorio
    	
    	final int cantidad_apuestas_maxima = misterioso.getMaximo_ticket();
    	final int cantidad_apuestas_minima = misterioso.getMinimo_ticket();
    	final int rango_apuestas = cantidad_apuestas_maxima - cantidad_apuestas_minima;
    	
    	SecureRandom secure = new SecureRandom();
    	
    	final int nextInt = secure.nextInt(rango_apuestas);
    	
    	final int nuevoGanador = nextInt + cantidad_apuestas_minima;
    	
    	misterioso.setGanador(nuevoGanador+"");//FIXME cambiar para que quede encriptado
    	
    }
    
    
    
    
	@Override
	@Transactional(readOnly = true)
	public List<Misterioso> findMisteriosoParticipa(int cantidadApuestas) {
		
		
		
		return misteriosoRepository.findByCantidadApuestasMax(cantidadApuestas);
	}
    
	/**
	 * @deprecated el metodo debe codificar el numero de ticket ganador
	 * @param misterioso
	 * @param cantidadTicket
	 * @return
	 */
    private boolean isGanador(Misterioso misterioso, int cantidadTicket) {
    	
    	int ticketGanador = Integer.parseInt(misterioso.getGanador()); 
    	
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
    
    private List<MisteriosoDTO> listEntityToListDTO(List<Misterioso> misteriosos){
    	
    	List<MisteriosoDTO> misteriososDtos = new ArrayList<>();
    	
    	for (Misterioso misterioso : misteriosos) {
    		final MisteriosoDTO misteriosoDto = misteriosoMapper.toDto(misterioso);
    		misteriososDtos.add(misteriosoDto);
		}
    	
    	return misteriososDtos;
    }
    
    
}
