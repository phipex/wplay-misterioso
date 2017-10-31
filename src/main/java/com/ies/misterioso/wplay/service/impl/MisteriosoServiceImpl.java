package com.ies.misterioso.wplay.service.impl;

import com.ies.misterioso.wplay.service.MisteriosoService;
import com.ies.misterioso.wplay.domain.Misterioso;
import com.ies.misterioso.wplay.repository.MisteriosoRepository;
import com.ies.misterioso.wplay.service.dto.MisteriosoDTO;
import com.ies.misterioso.wplay.service.mapper.MisteriosoMapper;
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
}
