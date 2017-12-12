package de.felixhoevel.application.web.rest;

import de.felixhoevel.application.GaffelCupApp;

import de.felixhoevel.application.domain.Contestant;
import de.felixhoevel.application.repository.ContestantRepository;
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

import de.felixhoevel.application.domain.enumeration.Strength;
/**
 * Test class for the ContestantResource REST controller.
 *
 * @see ContestantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GaffelCupApp.class)
public class ContestantResourceIntTest {

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_E_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_E_MAIL = "BBBBBBBBBB";

    private static final Strength DEFAULT_STRENGTH = Strength.LOW;
    private static final Strength UPDATED_STRENGTH = Strength.LOWMID;

    private static final Integer DEFAULT_TOTAL_POINTS = 1;
    private static final Integer UPDATED_TOTAL_POINTS = 2;

    private static final Boolean DEFAULT_CONFIRMED = false;
    private static final Boolean UPDATED_CONFIRMED = true;

    private static final ZonedDateTime DEFAULT_CONFIRMED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CONFIRMED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_PAYED = false;
    private static final Boolean UPDATED_PAYED = true;

    private static final ZonedDateTime DEFAULT_PAYED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PAYED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private ContestantRepository contestantRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContestantMockMvc;

    private Contestant contestant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContestantResource contestantResource = new ContestantResource(contestantRepository);
        this.restContestantMockMvc = MockMvcBuilders.standaloneSetup(contestantResource)
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
    public static Contestant createEntity(EntityManager em) {
        Contestant contestant = new Contestant()
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .eMail(DEFAULT_E_MAIL)
            .strength(DEFAULT_STRENGTH)
            .totalPoints(DEFAULT_TOTAL_POINTS)
            .confirmed(DEFAULT_CONFIRMED)
            .confirmedAt(DEFAULT_CONFIRMED_AT)
            .payed(DEFAULT_PAYED)
            .payedAt(DEFAULT_PAYED_AT)
            .token(DEFAULT_TOKEN);
        return contestant;
    }

    @Before
    public void initTest() {
        contestant = createEntity(em);
    }

    @Test
    @Transactional
    public void createContestant() throws Exception {
        int databaseSizeBeforeCreate = contestantRepository.findAll().size();

        // Create the Contestant
        restContestantMockMvc.perform(post("/api/contestants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contestant)))
            .andExpect(status().isCreated());

        // Validate the Contestant in the database
        List<Contestant> contestantList = contestantRepository.findAll();
        assertThat(contestantList).hasSize(databaseSizeBeforeCreate + 1);
        Contestant testContestant = contestantList.get(contestantList.size() - 1);
        assertThat(testContestant.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testContestant.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testContestant.geteMail()).isEqualTo(DEFAULT_E_MAIL);
        assertThat(testContestant.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testContestant.getTotalPoints()).isEqualTo(DEFAULT_TOTAL_POINTS);
        assertThat(testContestant.isConfirmed()).isEqualTo(DEFAULT_CONFIRMED);
        assertThat(testContestant.getConfirmedAt()).isEqualTo(DEFAULT_CONFIRMED_AT);
        assertThat(testContestant.isPayed()).isEqualTo(DEFAULT_PAYED);
        assertThat(testContestant.getPayedAt()).isEqualTo(DEFAULT_PAYED_AT);
        assertThat(testContestant.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    public void createContestantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contestantRepository.findAll().size();

        // Create the Contestant with an existing ID
        contestant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContestantMockMvc.perform(post("/api/contestants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contestant)))
            .andExpect(status().isBadRequest());

        // Validate the Contestant in the database
        List<Contestant> contestantList = contestantRepository.findAll();
        assertThat(contestantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContestants() throws Exception {
        // Initialize the database
        contestantRepository.saveAndFlush(contestant);

        // Get all the contestantList
        restContestantMockMvc.perform(get("/api/contestants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contestant.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].eMail").value(hasItem(DEFAULT_E_MAIL.toString())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH.toString())))
            .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS)))
            .andExpect(jsonPath("$.[*].confirmed").value(hasItem(DEFAULT_CONFIRMED.booleanValue())))
            .andExpect(jsonPath("$.[*].confirmedAt").value(hasItem(sameInstant(DEFAULT_CONFIRMED_AT))))
            .andExpect(jsonPath("$.[*].payed").value(hasItem(DEFAULT_PAYED.booleanValue())))
            .andExpect(jsonPath("$.[*].payedAt").value(hasItem(sameInstant(DEFAULT_PAYED_AT))))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }

    @Test
    @Transactional
    public void getContestant() throws Exception {
        // Initialize the database
        contestantRepository.saveAndFlush(contestant);

        // Get the contestant
        restContestantMockMvc.perform(get("/api/contestants/{id}", contestant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contestant.getId().intValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.eMail").value(DEFAULT_E_MAIL.toString()))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH.toString()))
            .andExpect(jsonPath("$.totalPoints").value(DEFAULT_TOTAL_POINTS))
            .andExpect(jsonPath("$.confirmed").value(DEFAULT_CONFIRMED.booleanValue()))
            .andExpect(jsonPath("$.confirmedAt").value(sameInstant(DEFAULT_CONFIRMED_AT)))
            .andExpect(jsonPath("$.payed").value(DEFAULT_PAYED.booleanValue()))
            .andExpect(jsonPath("$.payedAt").value(sameInstant(DEFAULT_PAYED_AT)))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContestant() throws Exception {
        // Get the contestant
        restContestantMockMvc.perform(get("/api/contestants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContestant() throws Exception {
        // Initialize the database
        contestantRepository.saveAndFlush(contestant);
        int databaseSizeBeforeUpdate = contestantRepository.findAll().size();

        // Update the contestant
        Contestant updatedContestant = contestantRepository.findOne(contestant.getId());
        // Disconnect from session so that the updates on updatedContestant are not directly saved in db
        em.detach(updatedContestant);
        updatedContestant
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .eMail(UPDATED_E_MAIL)
            .strength(UPDATED_STRENGTH)
            .totalPoints(UPDATED_TOTAL_POINTS)
            .confirmed(UPDATED_CONFIRMED)
            .confirmedAt(UPDATED_CONFIRMED_AT)
            .payed(UPDATED_PAYED)
            .payedAt(UPDATED_PAYED_AT)
            .token(UPDATED_TOKEN);

        restContestantMockMvc.perform(put("/api/contestants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContestant)))
            .andExpect(status().isOk());

        // Validate the Contestant in the database
        List<Contestant> contestantList = contestantRepository.findAll();
        assertThat(contestantList).hasSize(databaseSizeBeforeUpdate);
        Contestant testContestant = contestantList.get(contestantList.size() - 1);
        assertThat(testContestant.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testContestant.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testContestant.geteMail()).isEqualTo(UPDATED_E_MAIL);
        assertThat(testContestant.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testContestant.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testContestant.isConfirmed()).isEqualTo(UPDATED_CONFIRMED);
        assertThat(testContestant.getConfirmedAt()).isEqualTo(UPDATED_CONFIRMED_AT);
        assertThat(testContestant.isPayed()).isEqualTo(UPDATED_PAYED);
        assertThat(testContestant.getPayedAt()).isEqualTo(UPDATED_PAYED_AT);
        assertThat(testContestant.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingContestant() throws Exception {
        int databaseSizeBeforeUpdate = contestantRepository.findAll().size();

        // Create the Contestant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContestantMockMvc.perform(put("/api/contestants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contestant)))
            .andExpect(status().isCreated());

        // Validate the Contestant in the database
        List<Contestant> contestantList = contestantRepository.findAll();
        assertThat(contestantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContestant() throws Exception {
        // Initialize the database
        contestantRepository.saveAndFlush(contestant);
        int databaseSizeBeforeDelete = contestantRepository.findAll().size();

        // Get the contestant
        restContestantMockMvc.perform(delete("/api/contestants/{id}", contestant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contestant> contestantList = contestantRepository.findAll();
        assertThat(contestantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contestant.class);
        Contestant contestant1 = new Contestant();
        contestant1.setId(1L);
        Contestant contestant2 = new Contestant();
        contestant2.setId(contestant1.getId());
        assertThat(contestant1).isEqualTo(contestant2);
        contestant2.setId(2L);
        assertThat(contestant1).isNotEqualTo(contestant2);
        contestant1.setId(null);
        assertThat(contestant1).isNotEqualTo(contestant2);
    }
}
