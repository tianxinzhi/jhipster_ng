package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Spec;
import com.mycompany.myapp.service.SpecService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Spec}.
 */
@RestController
@RequestMapping("/api")
public class SpecResource {

    private final Logger log = LoggerFactory.getLogger(SpecResource.class);

    private static final String ENTITY_NAME = "spec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecService specService;

    public SpecResource(SpecService specService) {
        this.specService = specService;
    }

    /**
     * {@code POST  /specs} : Create a new spec.
     *
     * @param spec the spec to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spec, or with status {@code 400 (Bad Request)} if the spec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specs")
    public ResponseEntity<Spec> createSpec(@RequestBody Spec spec) throws URISyntaxException {
        log.debug("REST request to save Spec : {}", spec);
        if (spec.getId() != null) {
            throw new BadRequestAlertException("A new spec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Spec result = specService.save(spec);
        return ResponseEntity.created(new URI("/api/specs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specs} : Updates an existing spec.
     *
     * @param spec the spec to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spec,
     * or with status {@code 400 (Bad Request)} if the spec is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spec couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specs")
    public ResponseEntity<Spec> updateSpec(@RequestBody Spec spec) throws URISyntaxException {
        log.debug("REST request to update Spec : {}", spec);
        if (spec.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Spec result = specService.save(spec);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spec.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specs} : get all the specs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specs in body.
     */
    @GetMapping("/specs")
    public ResponseEntity<List<Spec>> getAllSpecs(Pageable pageable) {
        log.debug("REST request to get a page of Specs");
        Page<Spec> page = specService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specs/:id} : get the "id" spec.
     *
     * @param id the id of the spec to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spec, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specs/{id}")
    public ResponseEntity<Spec> getSpec(@PathVariable Long id) {
        log.debug("REST request to get Spec : {}", id);
        Optional<Spec> spec = specService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spec);
    }

    /**
     * {@code DELETE  /specs/:id} : delete the "id" spec.
     *
     * @param id the id of the spec to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specs/{id}")
    public ResponseEntity<Void> deleteSpec(@PathVariable Long id) {
        log.debug("REST request to delete Spec : {}", id);
        specService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
