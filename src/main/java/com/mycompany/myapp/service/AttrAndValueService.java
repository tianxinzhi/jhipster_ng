package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AttrAndValue;
import com.mycompany.myapp.domain.Spec;
import com.mycompany.myapp.repository.AttrAndValueRepository;
import com.mycompany.myapp.repository.SpecRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AttrAndValue}.
 */
@Service
@Transactional
public class AttrAndValueService {

    private final Logger log = LoggerFactory.getLogger(AttrAndValueService.class);

    private final AttrAndValueRepository attrAndValueRepository;

    public AttrAndValueService(AttrAndValueRepository attrAndValueRepository) {
        this.attrAndValueRepository = attrAndValueRepository;
    }

    /**
     * Save a attrAndValue.
     *
     * @param attrAndValue the entity to save.
     * @return the persisted entity.
     */
    public AttrAndValue save(AttrAndValue attrAndValue) {
        log.debug("Request to save AttrAndValue : {}", attrAndValue);
        return attrAndValueRepository.save(attrAndValue);
    }

    /**
     * Get all the attrAndValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttrAndValue> findAll(Pageable pageable) {
        log.debug("Request to get all AttrAndValues");
        return attrAndValueRepository.findAll(pageable);
    }

    /**
     * Get one attrAndValuec by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttrAndValue> findOne(Long id) {
        log.debug("Request to get AttrAndValue : {}", id);
        return attrAndValueRepository.findById(id);
    }

    /**
     * Delete the AttrAndValue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttrAndValue : {}", id);
        attrAndValueRepository.deleteById(id);
    }
}
