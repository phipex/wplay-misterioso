package com.ies.misterioso.wplay.service.mapper;

import com.ies.misterioso.wplay.domain.*;
import com.ies.misterioso.wplay.service.dto.MisteriosoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Misterioso and its DTO MisteriosoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MisteriosoMapper extends EntityMapper <MisteriosoDTO, Misterioso> {
    
    
    default Misterioso fromId(Long id) {
        if (id == null) {
            return null;
        }
        Misterioso misterioso = new Misterioso();
        misterioso.setId(id);
        return misterioso;
    }
}
