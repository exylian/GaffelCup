package de.felixhoevel.application.web.rest;

import de.felixhoevel.application.GaffelCupApp;

import de.felixhoevel.application.domain.GamePoints;
import de.felixhoevel.application.repository.GamePointsRepository;
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
import java.util.List;

import static de.felixhoevel.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GamePointsResource REST controller.
 *
 * @see GamePointsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GaffelCupApp.class)
public class GamePointsResourceIntTest {

    private static final Integer DEFAULT_S_1_T_1 = 1;
    private static final Integer UPDATED_S_1_T_1 = 2;

    private static final Integer DEFAULT_S_1_T_2 = 1;
    private static final Integer UPDATED_S_1_T_2 = 2;

    private static final Integer DEFAULT_S_2_T_1 = 1;
    private static final Integer UPDATED_S_2_T_1 = 2;

    private static final Integer DEFAULT_S_2_T_2 = 1;
    private static final Integer UPDATED_S_2_T_2 = 2;

    private static final Integer DEFAULT_S_3_T_1 = 1;
    private static final Integer UPDATED_S_3_T_1 = 2;

    private static final Integer DEFAULT_S_3_T_2 = 1;
    private static final Integer UPDATED_S_3_T_2 = 2;

    @Autowired
    private GamePointsRepository gamePointsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGamePointsMockMvc;

    private GamePoints gamePoints;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GamePointsResource gamePointsResource = new GamePointsResource(gamePointsRepository);
        this.restGamePointsMockMvc = MockMvcBuilders.standaloneSetup(gamePointsResource)
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
    public static GamePoints createEntity(EntityManager em) {
        GamePoints gamePoints = new GamePoints()
            .s1T1(DEFAULT_S_1_T_1)
            .s1T2(DEFAULT_S_1_T_2)
            .s2T1(DEFAULT_S_2_T_1)
            .s2T2(DEFAULT_S_2_T_2)
            .s3T1(DEFAULT_S_3_T_1)
            .s3T2(DEFAULT_S_3_T_2);
        return gamePoints;
    }

    @Before
    public void initTest() {
        gamePoints = createEntity(em);
    }

    @Test
    @Transactional
    public void createGamePoints() throws Exception {
        int databaseSizeBeforeCreate = gamePointsRepository.findAll().size();

        // Create the GamePoints
        restGamePointsMockMvc.perform(post("/api/game-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamePoints)))
            .andExpect(status().isCreated());

        // Validate the GamePoints in the database
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeCreate + 1);
        GamePoints testGamePoints = gamePointsList.get(gamePointsList.size() - 1);
        assertThat(testGamePoints.gets1T1()).isEqualTo(DEFAULT_S_1_T_1);
        assertThat(testGamePoints.gets1T2()).isEqualTo(DEFAULT_S_1_T_2);
        assertThat(testGamePoints.gets2T1()).isEqualTo(DEFAULT_S_2_T_1);
        assertThat(testGamePoints.gets2T2()).isEqualTo(DEFAULT_S_2_T_2);
        assertThat(testGamePoints.gets3T1()).isEqualTo(DEFAULT_S_3_T_1);
        assertThat(testGamePoints.gets3T2()).isEqualTo(DEFAULT_S_3_T_2);
    }

    @Test
    @Transactional
    public void createGamePointsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gamePointsRepository.findAll().size();

        // Create the GamePoints with an existing ID
        gamePoints.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGamePointsMockMvc.perform(post("/api/game-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamePoints)))
            .andExpect(status().isBadRequest());

        // Validate the GamePoints in the database
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGamePoints() throws Exception {
        // Initialize the database
        gamePointsRepository.saveAndFlush(gamePoints);

        // Get all the gamePointsList
        restGamePointsMockMvc.perform(get("/api/game-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gamePoints.getId().intValue())))
            .andExpect(jsonPath("$.[*].s1T1").value(hasItem(DEFAULT_S_1_T_1)))
            .andExpect(jsonPath("$.[*].s1T2").value(hasItem(DEFAULT_S_1_T_2)))
            .andExpect(jsonPath("$.[*].s2T1").value(hasItem(DEFAULT_S_2_T_1)))
            .andExpect(jsonPath("$.[*].s2T2").value(hasItem(DEFAULT_S_2_T_2)))
            .andExpect(jsonPath("$.[*].s3T1").value(hasItem(DEFAULT_S_3_T_1)))
            .andExpect(jsonPath("$.[*].s3T2").value(hasItem(DEFAULT_S_3_T_2)));
    }

    @Test
    @Transactional
    public void getGamePoints() throws Exception {
        // Initialize the database
        gamePointsRepository.saveAndFlush(gamePoints);

        // Get the gamePoints
        restGamePointsMockMvc.perform(get("/api/game-points/{id}", gamePoints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gamePoints.getId().intValue()))
            .andExpect(jsonPath("$.s1T1").value(DEFAULT_S_1_T_1))
            .andExpect(jsonPath("$.s1T2").value(DEFAULT_S_1_T_2))
            .andExpect(jsonPath("$.s2T1").value(DEFAULT_S_2_T_1))
            .andExpect(jsonPath("$.s2T2").value(DEFAULT_S_2_T_2))
            .andExpect(jsonPath("$.s3T1").value(DEFAULT_S_3_T_1))
            .andExpect(jsonPath("$.s3T2").value(DEFAULT_S_3_T_2));
    }

    @Test
    @Transactional
    public void getNonExistingGamePoints() throws Exception {
        // Get the gamePoints
        restGamePointsMockMvc.perform(get("/api/game-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGamePoints() throws Exception {
        // Initialize the database
        gamePointsRepository.saveAndFlush(gamePoints);
        int databaseSizeBeforeUpdate = gamePointsRepository.findAll().size();

        // Update the gamePoints
        GamePoints updatedGamePoints = gamePointsRepository.findOne(gamePoints.getId());
        // Disconnect from session so that the updates on updatedGamePoints are not directly saved in db
        em.detach(updatedGamePoints);
        updatedGamePoints
            .s1T1(UPDATED_S_1_T_1)
            .s1T2(UPDATED_S_1_T_2)
            .s2T1(UPDATED_S_2_T_1)
            .s2T2(UPDATED_S_2_T_2)
            .s3T1(UPDATED_S_3_T_1)
            .s3T2(UPDATED_S_3_T_2);

        restGamePointsMockMvc.perform(put("/api/game-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGamePoints)))
            .andExpect(status().isOk());

        // Validate the GamePoints in the database
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeUpdate);
        GamePoints testGamePoints = gamePointsList.get(gamePointsList.size() - 1);
        assertThat(testGamePoints.gets1T1()).isEqualTo(UPDATED_S_1_T_1);
        assertThat(testGamePoints.gets1T2()).isEqualTo(UPDATED_S_1_T_2);
        assertThat(testGamePoints.gets2T1()).isEqualTo(UPDATED_S_2_T_1);
        assertThat(testGamePoints.gets2T2()).isEqualTo(UPDATED_S_2_T_2);
        assertThat(testGamePoints.gets3T1()).isEqualTo(UPDATED_S_3_T_1);
        assertThat(testGamePoints.gets3T2()).isEqualTo(UPDATED_S_3_T_2);
    }

    @Test
    @Transactional
    public void updateNonExistingGamePoints() throws Exception {
        int databaseSizeBeforeUpdate = gamePointsRepository.findAll().size();

        // Create the GamePoints

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGamePointsMockMvc.perform(put("/api/game-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gamePoints)))
            .andExpect(status().isCreated());

        // Validate the GamePoints in the database
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGamePoints() throws Exception {
        // Initialize the database
        gamePointsRepository.saveAndFlush(gamePoints);
        int databaseSizeBeforeDelete = gamePointsRepository.findAll().size();

        // Get the gamePoints
        restGamePointsMockMvc.perform(delete("/api/game-points/{id}", gamePoints.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GamePoints> gamePointsList = gamePointsRepository.findAll();
        assertThat(gamePointsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GamePoints.class);
        GamePoints gamePoints1 = new GamePoints();
        gamePoints1.setId(1L);
        GamePoints gamePoints2 = new GamePoints();
        gamePoints2.setId(gamePoints1.getId());
        assertThat(gamePoints1).isEqualTo(gamePoints2);
        gamePoints2.setId(2L);
        assertThat(gamePoints1).isNotEqualTo(gamePoints2);
        gamePoints1.setId(null);
        assertThat(gamePoints1).isNotEqualTo(gamePoints2);
    }
}
