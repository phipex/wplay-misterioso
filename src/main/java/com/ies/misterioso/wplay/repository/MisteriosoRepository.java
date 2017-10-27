package com.ies.misterioso.wplay.repository;

import com.ies.misterioso.wplay.domain.Misterioso;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Misterioso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MisteriosoRepository extends JpaRepository<Misterioso, Long> {

	@Query("select m from Misterioso m where m.estado = 'ACTIVO' and ?1 between m.cantidad_apuestas_minima and m.cantidad_apuestas_maxima order by valor_base_max")
	List<Misterioso> findByCantidadApuestasMax(Integer numeroApuestas);
	
}
