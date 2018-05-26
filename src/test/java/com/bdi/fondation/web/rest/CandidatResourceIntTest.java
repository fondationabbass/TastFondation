package com.bdi.fondation.web.rest;

import com.bdi.fondation.TastFondationApp;

import com.bdi.fondation.domain.Candidat;
import com.bdi.fondation.repository.CandidatRepository;
import com.bdi.fondation.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.bdi.fondation.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CandidatResource REST controller.
 *
 * @see CandidatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TastFondationApp.class)
public class CandidatResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DNA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DNA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NA = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NA = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidatMockMvc;

    private Candidat candidat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidatResource candidatResource = new CandidatResource(candidatRepository);
        this.restCandidatMockMvc = MockMvcBuilders.standaloneSetup(candidatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidat createEntity(EntityManager em) {
        Candidat candidat = new Candidat()
            .nom(DEFAULT_NOM)
            .dna(DEFAULT_DNA)
            .lieuNa(DEFAULT_LIEU_NA)
            .adresse(DEFAULT_ADRESSE);
        return candidat;
    }

    @Before
    public void initTest() {
        candidat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidat() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isCreated());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate + 1);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testCandidat.getDna()).isEqualTo(DEFAULT_DNA);
        assertThat(testCandidat.getLieuNa()).isEqualTo(DEFAULT_LIEU_NA);
        assertThat(testCandidat.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createCandidatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidatRepository.findAll().size();

        // Create the Candidat with an existing ID
        candidat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidatMockMvc.perform(post("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isBadRequest());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandidats() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get all the candidatList
        restCandidatMockMvc.perform(get("/api/candidats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].dna").value(hasItem(DEFAULT_DNA.toString())))
            .andExpect(jsonPath("$.[*].lieuNa").value(hasItem(DEFAULT_LIEU_NA.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }

    @Test
    @Transactional
    public void getCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);

        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", candidat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidat.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.dna").value(DEFAULT_DNA.toString()))
            .andExpect(jsonPath("$.lieuNa").value(DEFAULT_LIEU_NA.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidat() throws Exception {
        // Get the candidat
        restCandidatMockMvc.perform(get("/api/candidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);
        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Update the candidat
        Candidat updatedCandidat = candidatRepository.findOne(candidat.getId());
        // Disconnect from session so that the updates on updatedCandidat are not directly saved in db
        em.detach(updatedCandidat);
        updatedCandidat
            .nom(UPDATED_NOM)
            .dna(UPDATED_DNA)
            .lieuNa(UPDATED_LIEU_NA)
            .adresse(UPDATED_ADRESSE);

        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidat)))
            .andExpect(status().isOk());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate);
        Candidat testCandidat = candidatList.get(candidatList.size() - 1);
        assertThat(testCandidat.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testCandidat.getDna()).isEqualTo(UPDATED_DNA);
        assertThat(testCandidat.getLieuNa()).isEqualTo(UPDATED_LIEU_NA);
        assertThat(testCandidat.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidat() throws Exception {
        int databaseSizeBeforeUpdate = candidatRepository.findAll().size();

        // Create the Candidat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandidatMockMvc.perform(put("/api/candidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidat)))
            .andExpect(status().isCreated());

        // Validate the Candidat in the database
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandidat() throws Exception {
        // Initialize the database
        candidatRepository.saveAndFlush(candidat);
        int databaseSizeBeforeDelete = candidatRepository.findAll().size();

        // Get the candidat
        restCandidatMockMvc.perform(delete("/api/candidats/{id}", candidat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Candidat> candidatList = candidatRepository.findAll();
        assertThat(candidatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Candidat.class);
        Candidat candidat1 = new Candidat();
        candidat1.setId(1L);
        Candidat candidat2 = new Candidat();
        candidat2.setId(candidat1.getId());
        assertThat(candidat1).isEqualTo(candidat2);
        candidat2.setId(2L);
        assertThat(candidat1).isNotEqualTo(candidat2);
        candidat1.setId(null);
        assertThat(candidat1).isNotEqualTo(candidat2);
    }
}
