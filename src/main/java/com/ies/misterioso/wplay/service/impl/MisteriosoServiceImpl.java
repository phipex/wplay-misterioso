package com.ies.misterioso.wplay.service.impl;

import com.ies.misterioso.wplay.service.MisteriosoService;

import com.ies.misterioso.wplay.domain.Misterioso;
import com.ies.misterioso.wplay.domain.Ticket;

import com.ies.misterioso.wplay.domain.enumeration.EstadoGanador;
import com.ies.misterioso.wplay.repository.MisteriosoRepository;
import com.ies.misterioso.wplay.service.dto.MisteriosoDTO;
import com.ies.misterioso.wplay.service.dto.MisteriosoTicketDTO;
import com.ies.misterioso.wplay.service.dto.RetornoTicketDTO;
import com.ies.misterioso.wplay.service.dto.TicketGanadorDTO;
import com.ies.misterioso.wplay.service.mapper.MisteriosoMapper;
import com.ies.misterioso.wplay.service.mapper.MisteriosoTicketMapper;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Misterioso.
 */
@Service
@Scope("singleton")
public class MisteriosoServiceImpl implements MisteriosoService{

    private static final int MAXIMA_UNIDAD_DESPUES_LIMITE = 1;

	private final Logger log = LoggerFactory.getLogger(MisteriosoServiceImpl.class);

    private final MisteriosoRepository misteriosoRepository;

    private final MisteriosoMapper misteriosoMapper;
    
    private final MisteriosoTicketMapper misteriosoTicketMapper;

    /**
     * contiene los ganadores para cada misterioso <idMisterioso, Ganador>
     */
    private static volatile ConcurrentMap<Long, AtomicLong> ganadores;
    
    /**
     * contiene el ultimo ganador <idMisterioso, Ganador>
     */
	private static volatile ConcurrentMap<Long, AtomicLong> ultimosGanadores;
    
	/**
	 * indice de los tickets para cada misterioso <idMisterioso, ContadorTickect>
	 */
    private static volatile ConcurrentMap<Long, LongAdder> indexTicketByMisterioso;
    
    private static final synchronized ConcurrentMap<Long, AtomicLong> getGanadores() {
		if(ganadores == null) {
			ganadores = new ConcurrentHashMap<>();
		}
        	
    	return ganadores;
	}
    
    private static final synchronized ConcurrentMap<Long, AtomicLong> getUltimosGanadores() {
		
    	if(ultimosGanadores == null) {
    		ultimosGanadores = new ConcurrentHashMap<>();
    	}
    	
    	return ultimosGanadores;
	}

	private static final synchronized ConcurrentMap<Long, LongAdder> getIndexTicketByMisterioso() {
		if(indexTicketByMisterioso == null) {
			indexTicketByMisterioso = new ConcurrentHashMap<>();
		}
		
		return indexTicketByMisterioso;
	}

	public MisteriosoServiceImpl(MisteriosoRepository misteriosoRepository, MisteriosoMapper misteriosoMapper, MisteriosoTicketMapper misteriosoTicketMapper) {
        this.misteriosoRepository = misteriosoRepository;
        this.misteriosoMapper = misteriosoMapper;
        this.misteriosoTicketMapper = misteriosoTicketMapper;
        
    }

    /**
     * Save a misterioso.
     *
     * @param misteriosoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public synchronized MisteriosoDTO save(MisteriosoDTO misteriosoDTO) {
        
    	//TODO actualizar el mapa
    	
    	return saveRepository(misteriosoDTO);
    }

	private MisteriosoDTO saveRepository(MisteriosoDTO misteriosoDTO) {
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
    
    /**
     * 
     */
    @Override
    @Transactional
    public synchronized RetornoTicketDTO verificaGanador(Ticket ticket) {
    	
    	RetornoTicketDTO retornoGanador = new RetornoTicketDTO();
    	
    	TicketGanadorDTO ticketGanadorDto = null;
    	
    	//traer los misteriosos que participan
    	final Integer cantidadApuestas = ticket.getCantidad_apuestas();
    	
    	final List<Misterioso> misteriososParticipa = findMisteriosoParticipa(cantidadApuestas);
    	
    	//para cada misterioso en que participa
    	for (Misterioso misterioso : misteriososParticipa) {
    		TicketGanadorDTO ticketGanadorTemDto = new TicketGanadorDTO();
    		boolean salir = analizaTicketByMisterioso(misterioso, ticket, ticketGanadorTemDto);
    		if(salir) {
    			ticketGanadorDto = ticketGanadorTemDto;
    			
    			log.debug("ticketGanadorDto ---------{}",ticketGanadorDto);
    			break;
    		}
    		
		}
    	
    	retornoGanador.setListaMisteriososActualizados(listEntityToListDTO(misteriososParticipa));
    	
    	retornoGanador.setGanadorDTO(ticketGanadorDto);
    	
    	
    	return retornoGanador;
    }    
    
    /**
     * 
     * @param misterioso
     * @param ticket
     * @param ticketGanadorDto
     * @return
     */
    @Transactional
    public synchronized boolean analizaTicketByMisterioso(Misterioso misterioso, Ticket ticket, TicketGanadorDTO ticketGanadorDto) {
    	boolean salir = false;

		// aumentar el contador de tickets
		int cantidadTicket = (int) incrementTicketMisterioso(misterioso);
		
		//verificar que no se pase
		int maximoTicket = misterioso.getMaximo_ticket() + MAXIMA_UNIDAD_DESPUES_LIMITE + 1;
		
		boolean superaLimite = maximoTicket <= cantidadTicket;
		
		log.debug("analizaTicketByMisterioso::maximoTicket {} cantidadTicket {} superaLimite {}",maximoTicket,cantidadTicket, superaLimite);
		
		if(superaLimite) {
			
			log.debug("supera limite --------");
			
			reiniciaMisterioso(misterioso);
			
			return salir;//analizaTicketByMisterioso(misterioso, ticket, ticketGanadorDto);
			
		}
		
		
		// actualizar el ticket con la info de los misteriosos que participa
    	String misteriosoParticipa = ticket.getParticipa_misterioso();
		misteriosoParticipa = misteriosoParticipa + Long.toString(misterioso.getId())+"("+cantidadTicket+"),";
		ticket.setParticipa_misterioso(misteriosoParticipa);
		
    	//verificar si es ganador
		boolean isGanador = isGanador(misterioso, cantidadTicket);
		
		log.debug("analizaTicketByMisterioso::isganador {} cantidadTicket",isGanador,cantidadTicket);
		
		if(isGanador) {//si es ganador
			//crear el registro de ganador
			EstadoGanador estado = EstadoGanador.GANADO; 
			BigDecimal valorGanado = misterioso.getAcumulado();
			String descripcion =  null;
			Integer indiceTicketMisterioso = cantidadTicket;
			
			//ticketGanadorDto = new TicketGanadorDTO(estado, valorGanado, descripcion,indiceTicketMisterioso, misterioso, ticket);
			ticketGanadorDto.setEstado(estado);
			ticketGanadorDto.setValor_ganado(valorGanado);
			ticketGanadorDto.setDescripcion(descripcion);
			ticketGanadorDto.setIndice_ticket_misterioso(indiceTicketMisterioso);
			ticketGanadorDto.setMisteriosoId(misterioso.getId());
			ticketGanadorDto.setTicketId(ticket.getId());
			ticketGanadorDto.setId(0L);
			ticketGanadorDto.setFecha(ZonedDateTime.now());
			
			log.debug("analizaTicketByMisterioso::ticketGanadorDto {}",ticketGanadorDto);
			
			//reinicia los valores del misterioso
			reiniciaMisterioso(misterioso);
			
			salir = true;
			
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
    	return salir;
    }
    
    
    /**
     * 
     * @param misterioso
     * @return
     */
    @Transactional
    public synchronized long incrementTicketMisterioso(Misterioso misterioso) {
    	
    	LongAdder contadorTicket = null;
    	
    	Long idMisterioso = misterioso.getId();
    	
    	int cantidadApuestasMaxima = misterioso.getMaximo_ticket() + MAXIMA_UNIDAD_DESPUES_LIMITE;
    	
    	boolean noContainsKey = !MisteriosoServiceImpl.getIndexTicketByMisterioso().containsKey(idMisterioso);
    	
    	//log.debug("incrementTicketMisterioso::incrementTicketMisterioso::indice de contadores de ticket {}",indexTicketByMisterioso);
    	
    	if(noContainsKey) {
    		
    		int cantidadApuestas = misterioso.getCantidad_apuestas();
    		
    		contadorTicket = new LongAdder();
    		contadorTicket.add(cantidadApuestas);
    		MisteriosoServiceImpl.getIndexTicketByMisterioso().putIfAbsent(idMisterioso, contadorTicket);
    		
    		
    	}else {
    		contadorTicket = MisteriosoServiceImpl.getIndexTicketByMisterioso().get(idMisterioso);
    		
    	}
    	
    	log.debug("incrementTicketMisterioso::incrementTicketMisterioso::cantidad maxima {}  contador {}",cantidadApuestasMaxima,contadorTicket.sum());
    	  	
    	
    	if(cantidadApuestasMaxima >= contadorTicket.sum()) {
    		   		
    		contadorTicket.increment();
    		
    		log.debug("incrementTicketMisterioso::incrementTicketMisterioso::incremento cantidadApuestasMaxima >= contadorTicket.sum() {}",contadorTicket.sum());
    	}
    	
    	return contadorTicket.sum();
    }
    
    /**
     * 
     * @param misterioso
     */
    @Transactional
    public synchronized void reiniciaMisterioso(Misterioso misterioso) {
    	
    	log.debug("reiniciaMisterioso::reiniciando misterioso {}",misterioso);

    	//reiniciar el acumulado
    	
    	BigDecimal nuevoAcumulado = misterioso.getValor_base_min();
    	
    	misterioso.setAcumulado(nuevoAcumulado);
    	
    	Long idMisterioso = misterioso.getId();
    	
    	//crear nuevo ganador aleatorio
    	
    	final long nuevoGanador = nuevoGanador(misterioso);
    	
    	//AtomicLong atomicNuevoGanador = new AtomicLong(nuevoGanador);
    	
    	misterioso.setGanador(nuevoGanador+"");//FIXME cambiar para que quede encriptado
    	
    	//MisteriosoServiceImpl.getGanadores().putIfAbsent(idMisterioso, atomicNuevoGanador);
    	final AtomicLong atomicGanador = MisteriosoServiceImpl.getGanadores().get(idMisterioso);
    	
    	atomicGanador.set(nuevoGanador);
    	
    	//log.debug("reiniciaMisterioso::ganadores {}",MisteriosoServiceImpl.getGanadores());
    	
    	//reiniciar la cantidad de ticket
    	
    	//indexTicketByMisterioso.putIfAbsent(idMisterioso, new LongAdder());
    	LongAdder longAdder = MisteriosoServiceImpl.getIndexTicketByMisterioso().get(idMisterioso);
    	longAdder.reset();
    	
    	misterioso.setCantidad_apuestas(0);
    	
    	
    	//log.debug("reiniciaMisterioso::indexTicketByMisterioso {}",MisteriosoServiceImpl.getIndexTicketByMisterioso());
    	
    	misteriosoRepository.save(misterioso);
    	
    	  
    	
    	log.debug("reiniciaMisterioso::reiniciando misterioso {}",misterioso);
    	
    }

	/**
	 * @param misterioso
	 * @return
	 */
	private long nuevoGanador(Misterioso misterioso) {
		final int cantidadApuestasMaxima = misterioso.getMaximo_ticket();
    	final int cantidadApuestasMinima = misterioso.getMinimo_ticket();
    	final int rangoApuestas = cantidadApuestasMaxima - cantidadApuestasMinima;
    	
    	SecureRandom secure = new SecureRandom();
    	
    	final long nextInt = (long)secure.nextInt(rangoApuestas);
    	
    	final long nuevoGanador = nextInt + (long)cantidadApuestasMinima;
		return nuevoGanador;
	}
    
    
    
    /**
     * 
     */
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
	@Transactional
    public synchronized boolean isGanador(Misterioso misterioso, int cantidadTicket) {
    	
		log.debug("isGanador::cantidad tikect {} misterioso {}",cantidadTicket, misterioso.getId());
		
    	boolean isGanador = false;
    	
    	Long idMisterioso = misterioso.getId();
    	
    	Long ticketGanador = null;
    	
    	// validacion que verifique que esta dentro del rango
		final int cantidadApuestasMaxima = misterioso.getMaximo_ticket();
    	final int cantidadApuestasMinima = misterioso.getMinimo_ticket();
		
    	if(cantidadTicket < cantidadApuestasMinima) {
			return isGanador;
		}
		
    	boolean containsKey = MisteriosoServiceImpl.getGanadores().containsKey(idMisterioso);
    	
    	if(containsKey) {
    		
    		ticketGanador = MisteriosoServiceImpl.getGanadores().get(idMisterioso).get();
    		
    	}else {
    		String ganadorMisterioso = misterioso.getGanador();
    		
        	ticketGanador = Long.parseLong(ganadorMisterioso);
        	
        	MisteriosoServiceImpl.getGanadores().putIfAbsent(idMisterioso, new AtomicLong(ticketGanador));
    	}
    	
    	//String ganador = ticketGanador+"";
    	
    	boolean dentroRango = cantidadTicket < cantidadApuestasMaxima;
		
    	boolean diferenteCantidad = ticketGanador != cantidadTicket;
    	
    	log.debug("isGanador::dentro rango {}",dentroRango);
    	
    	log.debug("isGanador::diferente cantidad? {} != {} => {}",ticketGanador,cantidadTicket,diferenteCantidad);
    	
    	log.debug("isGanador::cantidad tikect {}",cantidadTicket);
    	
    	if(diferenteCantidad && dentroRango) {
    		
    		log.debug("isGanador::diferente cantidad, no es ganador");
    		return isGanador;
    		
    	}	
		
		
		boolean noContainsKey = !MisteriosoServiceImpl.getUltimosGanadores().containsKey(idMisterioso);
		
		if(noContainsKey) {
			
			isGanador = true;
			MisteriosoServiceImpl.getUltimosGanadores().putIfAbsent(idMisterioso, new AtomicLong(ticketGanador));
			//log.debug("isGanador::estado actual del map {}",MisteriosoServiceImpl.getUltimosGanadores());
		}else {
			Long ultimoGanador = MisteriosoServiceImpl.getUltimosGanadores().get(idMisterioso).get();
			
			isGanador = !ticketGanador.equals(ultimoGanador);
			
			log.debug("isGanador::ultimo ganador {}, ganador que pregunta {}, isganador {}",ultimoGanador,ticketGanador,isGanador);
			
			if(isGanador) {
				MisteriosoServiceImpl.getUltimosGanadores().putIfAbsent(idMisterioso, new AtomicLong(ticketGanador));
				isGanador = true;
			}
			//log.debug("isGanador::estado actual del map {}",MisteriosoServiceImpl.getUltimosGanadores());
			
		}
    	
		return isGanador;
    }
    
    /**
     * @deprecated TODO hay que pasar a un archivo utils
     * @param base
     * @param pct
     * @return
     */
    public static synchronized BigDecimal percentage(BigDecimal base, BigDecimal pct){
        return base.multiply(pct).divide(new BigDecimal(100));
    }
    /**
     * 
     * @param misteriosos
     * @return
     */
    private synchronized List<MisteriosoTicketDTO> listEntityToListDTO(List<Misterioso> misteriosos){
    	
    	List<MisteriosoTicketDTO> misteriososDtos = new ArrayList<>();
    	
    	for (Misterioso misterioso : misteriosos) {
    		final MisteriosoTicketDTO misteriosoDto = misteriosoTicketMapper.toDto(misterioso);
    		misteriososDtos.add(misteriosoDto);
		}
    	
    	return misteriososDtos;
    }
    
    
}
