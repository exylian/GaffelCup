package de.felixhoevel.application.web.rest;

import de.felixhoevel.application.GaffelCupApp;

import de.felixhoevel.application.domain.Round;
import de.felixhoevel.application.repository.RoundRepository;
import de.felixhoevel.application.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static de.felixhoevel.application.web.rest.TestUtil.sameInstant;
import static de.felixhoevel.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.felixhoevel.application.domain.enumeration.Status;
/**
 * Test class for the RoundResource REST controller.
 *
 * @see RoundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GaffelCupApp.class)
public class RoundResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.OPEN;
    private static final Status UPDATED_STATUS = Status.CLOSED;

    private static final ZonedDateTime DEFAULT_FINISHED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FINISHED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRoundMockMvc;

    private Round round;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoundResource roundResource = new RoundResource(roundRepository);
        this.restRoundMockMvc = MockMvcBuilders.standaloneSetup(roundResource)
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
    public static Round createEntity(EntityManager em) {
        Round round = new Round()
            .name(DEFAULT_NAME)
            .status(DEFAULT_STATUS)
            .finishedAt(DEFAULT_FINISHED_AT);
        return round;
    }

    @Before
    public void initTest() {
        round = createEntity(em);
    }

    @Test
    @Transactional
    public void createRound() throws Exception {
        int databaseSizeBeforeCreate = roundRepository.findAll().size();

        // Create the Round
        restRoundMockMvc.perform(post("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isCreated());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeCreate + 1);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRound.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRound.getFinishedAt()).isEqualTo(DEFAULT_FINISHED_AT);
    }

    @Test
    @Transactional
    public void createRoundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roundRepository.findAll().size();

        // Create the Round with an existing ID
        round.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoundMockMvc.perform(post("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isBadRequest());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRounds() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get all the roundList
        restRoundMockMvc.perform(get("/api/rounds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(round.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(sameInstant(DEFAULT_FINISHED_AT))));
    }

    @Test
    @Transactional
    public void getRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);

        // Get the round
        restRoundMockMvc.perform(get("/api/rounds/{id}", round.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(round.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.finishedAt").value(sameInstant(DEFAULT_FINISHED_AT)));
    }

    @Test
    @Transactional
    public void getNonExistingRound() throws Exception {
        // Get the round
        restRoundMockMvc.perform(get("/api/rounds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Update the round
        Round updatedRound = roundRepository.findOne(round.getId());
        // Disconnect from session so that the updates on updatedRound are not directly saved in db
        em.detach(updatedRound);
        updatedRound
            .name(UPDATED_NAME)
            .status(UPDATED_STATUS)
            .finishedAt(UPDATED_FINISHED_AT);

        restRoundMockMvc.perform(put("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRound)))
            .andExpect(status().isOk());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate);
        Round testRound = roundList.get(roundList.size() - 1);
        assertThat(testRound.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRound.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRound.getFinishedAt()).isEqualTo(UPDATED_FINISHED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingRound() throws Exception {
        int databaseSizeBeforeUpdate = roundRepository.findAll().size();

        // Create the Round

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoundMockMvc.perform(put("/api/rounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(round)))
            .andExpect(status().isCreated());

        // Validate the Round in the database
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRound() throws Exception {
        // Initialize the database
        roundRepository.saveAndFlush(round);
        int databaseSizeBeforeDelete = roundRepository.findAll().size();

        // Get the round
        restRoundMockMvc.perform(delete("/api/rounds/{id}", round.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Round> roundList = roundRepository.findAll();
        assertThat(roundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Round.class);
        Round round1 = new Round();
        round1.setId(1L);
        Round round2 = new Round();
        round2.setId(round1.getId());
        assertThat(round1).isEqualTo(round2);
        round2.setId(2L);
        assertThat(round1).isNotEqualTo(round2);
        round1.setId(null);
        assertThat(round1).isNotEqualTo(round2);
    }
}
