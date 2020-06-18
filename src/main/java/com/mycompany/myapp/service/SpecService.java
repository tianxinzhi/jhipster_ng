package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Spec;
import com.mycompany.myapp.repository.SpecRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Spec}.
 */
@Service
@Transactional
public class SpecService {

    private final Logger log = LoggerFactory.getLogger(SpecService.class);

    private final SpecRepository specRepository;

    public SpecService(SpecRepository specRepository) {
        this.specRepository = specRepository;
    }

    /**
     * Save a spec.
     *
     * @param spec the entity to save.
     * @return the persisted entity.
     */
    public Spec save(Spec spec) {
        log.debug("Request to save Spec : {}", spec);
        return specRepository.save(spec);
    }

    /**
     * Get all the specs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Spec> findAll(Pageable pageable) {
        log.debug("Request to get all Specs");
        return specRepository.findAll(pageable);
    }

    /**
     * Get one spec by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Spec> findOne(Long id) {
        log.debug("Request to get Spec : {}", id);
        return specRepository.findById(id);
    }

    /**
     * Delete the spec by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Spec : {}", id);
        specRepository.deleteById(id);
    }
}
