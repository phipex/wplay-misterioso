package com.ies.misterioso.wplay.repository;

import com.ies.misterioso.wplay.domain.TicketGanador;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TicketGanador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketGanadorRepository extends JpaRepository<TicketGanador, Long> {

}
