package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.RedisTestContainerExtension;
import com.mycompany.myapp.DemoNgApp;
import com.mycompany.myapp.domain.Spec;
import com.mycompany.myapp.repository.SpecRepository;
import com.mycompany.myapp.service.SpecService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SpecResource} REST controller.
 */
@SpringBootTest(classes = DemoNgApp.class)
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class SpecResourceIT {

    private static final String DEFAULT_SPEC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SPEC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SPEC_DESC = "AAAAAAAAAA";
    private static final String UPDATED_SPEC_DESC = "BBBBBBBBBB";

    @Autowired
    private SpecRepository specRepository;

    @Autowired
    private SpecService specService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecMockMvc;

    private Spec spec;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spec createEntity(EntityManager em) {
        Spec spec = new Spec()
            .specName(DEFAULT_SPEC_NAME)
            .specDesc(DEFAULT_SPEC_DESC);
        return spec;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spec createUpdatedEntity(EntityManager em) {
        Spec spec = new Spec()
            .specName(UPDATED_SPEC_NAME)
            .specDesc(UPDATED_SPEC_DESC);
        return spec;
    }

    @BeforeEach
    public void initTest() {
        spec = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpec() throws Exception {
        int databaseSizeBeforeCreate = specRepository.findAll().size();

        // Create the Spec
        restSpecMockMvc.perform(post("/api/specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(spec)))
            .andExpect(status().isCreated());

        // Validate the Spec in the database
        List<Spec> specList = specRepository.findAll();
        assertThat(specList).hasSize(databaseSizeBeforeCreate + 1);
        Spec testSpec = specList.get(specList.size() - 1);
        assertThat(testSpec.getSpecName()).isEqualTo(DEFAULT_SPEC_NAME);
        assertThat(testSpec.getSpecDesc()).isEqualTo(DEFAULT_SPEC_DESC);
    }

    @Test
    @Transactional
    public void createSpecWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specRepository.findAll().size();

        // Create the Spec with an existing ID
        spec.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecMockMvc.perform(post("/api/specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(spec)))
            .andExpect(status().isBadRequest());

        // Validate the Spec in the database
        List<Spec> specList = specRepository.findAll();
        assertThat(specList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSpecs() throws Exception {
        // Initialize the database
        specRepository.saveAndFlush(spec);

        // Get all the specList
        restSpecMockMvc.perform(get("/api/specs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spec.getId().intValue())))
            .andExpect(jsonPath("$.[*].specName").value(hasItem(DEFAULT_SPEC_NAME)))
            .andExpect(jsonPath("$.[*].specDesc").value(hasItem(DEFAULT_SPEC_DESC)));
    }
    
    @Test
    @Transactional
    public void getSpec() throws Exception {
        // Initialize the database
        specRepository.saveAndFlush(spec);

        // Get the spec
        restSpecMockMvc.perform(get("/api/specs/{id}", spec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(spec.getId().intValue()))
            .andExpect(jsonPath("$.specName").value(DEFAULT_SPEC_NAME))
            .andExpect(jsonPath("$.specDesc").value(DEFAULT_SPEC_DESC));
    }

    @Test
    @Transactional
    public void getNonExistingSpec() throws Exception {
        // Get the spec
        restSpecMockMvc.perform(get("/api/specs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpec() throws Exception {
        // Initialize the database
        specService.save(spec);

        int databaseSizeBeforeUpdate = specRepository.findAll().size();

        // Update the spec
        Spec updatedSpec = specRepository.findById(spec.getId()).get();
        // Disconnect from session so that the updates on updatedSpec are not directly saved in db
        em.detach(updatedSpec);
        updatedSpec
            .specName(UPDATED_SPEC_NAME)
            .specDesc(UPDATED_SPEC_DESC);

        restSpecMockMvc.perform(put("/api/specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpec)))
            .andExpect(status().isOk());

        // Validate the Spec in the database
        List<Spec> specList = specRepository.findAll();
        assertThat(specList).hasSize(databaseSizeBeforeUpdate);
        Spec testSpec = specList.get(specList.size() - 1);
        assertThat(testSpec.getSpecName()).isEqualTo(UPDATED_SPEC_NAME);
        assertThat(testSpec.getSpecDesc()).isEqualTo(UPDATED_SPEC_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingSpec() throws Exception {
        int databaseSizeBeforeUpdate = specRepository.findAll().size();

        // Create the Spec

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecMockMvc.perform(put("/api/specs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(spec)))
            .andExpect(status().isBadRequest());

        // Validate the Spec in the database
        List<Spec> specList = specRepository.findAll();
        assertThat(specList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpec() throws Exception {
        // Initialize the database
        specService.save(spec);

        int databaseSizeBeforeDelete = specRepository.findAll().size();

        // Delete the spec
        restSpecMockMvc.perform(delete("/api/specs/{id}", spec.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Spec> specList = specRepository.findAll();
        assertThat(specList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
