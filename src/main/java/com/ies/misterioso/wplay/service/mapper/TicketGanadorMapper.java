package com.ies.misterioso.wplay.service.mapper;

import com.ies.misterioso.wplay.domain.*;
import com.ies.misterioso.wplay.service.dto.TicketGanadorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TicketGanador and its DTO TicketGanadorDTO.
 */
@Mapper(componentModel = "spring", uses = {MisteriosoMapper.class, TicketMapper.class, })
public interface TicketGanadorMapper extends EntityMapper <TicketGanadorDTO, TicketGanador> {

    @Mapping(source = "misterioso.id", target = "misteriosoId")

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketGanadorDTO toDto(TicketGanador ticketGanador); 

    @Mapping(source = "misteriosoId", target = "misterioso")

    @Mapping(source = "ticketId", target = "ticket")
    TicketGanador toEntity(TicketGanadorDTO ticketGanadorDTO); 
    default TicketGanador fromId(Long id) {
        if (id == null) {
            return null;
        }
        TicketGanador ticketGanador = new TicketGanador();
        ticketGanador.setId(id);
        return ticketGanador;
    }
}
