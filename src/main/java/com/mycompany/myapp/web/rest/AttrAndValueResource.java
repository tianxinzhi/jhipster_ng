package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AttrAndValue;
import com.mycompany.myapp.domain.Spec;
import com.mycompany.myapp.service.AttrAndValueService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link Spec}.
 */
@RestController
@RequestMapping("/api")
public class AttrAndValueResource {

    private final Logger log = LoggerFactory.getLogger(AttrAndValueResource.class);

    private static final String ENTITY_NAME = "attrAndValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttrAndValueService attrAndValueService;

    public AttrAndValueResource(AttrAndValueService attrAndValueService) {
        this.attrAndValueService = attrAndValueService;
    }

    /**
     * {@code POST  /AttrAndValues} : Create a new AttrAndValue.
     *
     * @param attrAndValue the attrAndValue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new AttrAndValue, or with status {@code 400 (Bad Request)} if the spec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attr-and-values")
    public ResponseEntity<AttrAndValue> createAttrAndValue(@RequestBody AttrAndValue attrAndValue) throws URISyntaxException {
        log.debug("REST request to save Spec : {}", attrAndValue);
        if (attrAndValue.getId() != null) {
            throw new BadRequestAlertException("A new attrAndValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttrAndValue result = attrAndValueService.save(attrAndValue);
        return ResponseEntity.created(new URI("/api/attrAndValues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specs} : Updates an existing spec.
     *
     * @param attrAndValue the spec to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spec,
     * or with status {@code 400 (Bad Request)} if the spec is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spec couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attr-and-values")
    public ResponseEntity<AttrAndValue> updateSpec(@RequestBody AttrAndValue attrAndValue) throws URISyntaxException {
        log.debug("REST request to update AttrAndValue : {}", attrAndValue);
        if (attrAndValue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttrAndValue result = attrAndValueService.save(attrAndValue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attrAndValue.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specs} : get all the specs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specs in body.
     */
    @GetMapping("/attr-and-values")
    public ResponseEntity<List<AttrAndValue>> getAllAttrAndValues(Pageable pageable) {
        log.debug("REST request to get a page of Specs");
        Page<AttrAndValue> page = attrAndValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specs/:id} : get the "id" spec.
     *
     * @param id the id of the spec to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spec, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attr-and-values/{id}")
    public ResponseEntity<AttrAndValue> getAttrAndValue(@PathVariable Long id) {
        log.debug("REST request to get AttrAndValue : {}", id);
        Optional<AttrAndValue> spec = attrAndValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spec);
    }

    /**
     * {@code DELETE  /specs/:id} : delete the "id" spec.
     *
     * @param id the id of the spec to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attr-and-values/{id}")
    public ResponseEntity<Void> deleteAttrAndValue(@PathVariable Long id) {
        log.debug("REST request to delete AttrAndValue : {}", id);
        attrAndValueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
