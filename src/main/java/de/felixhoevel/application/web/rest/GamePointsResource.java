package de.felixhoevel.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.felixhoevel.application.domain.GamePoints;

import de.felixhoevel.application.repository.GamePointsRepository;
import de.felixhoevel.application.web.rest.errors.BadRequestAlertException;
import de.felixhoevel.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GamePoints.
 */
@RestController
@RequestMapping("/api")
public class GamePointsResource {

    private final Logger log = LoggerFactory.getLogger(GamePointsResource.class);

    private static final String ENTITY_NAME = "gamePoints";

    private final GamePointsRepository gamePointsRepository;

    public GamePointsResource(GamePointsRepository gamePointsRepository) {
        this.gamePointsRepository = gamePointsRepository;
    }

    /**
     * POST  /game-points : Create a new gamePoints.
     *
     * @param gamePoints the gamePoints to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gamePoints, or with status 400 (Bad Request) if the gamePoints has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/game-points")
    @Timed
    public ResponseEntity<GamePoints> createGamePoints(@RequestBody GamePoints gamePoints) throws URISyntaxException {
        log.debug("REST request to save GamePoints : {}", gamePoints);
        if (gamePoints.getId() != null) {
            throw new BadRequestAlertException("A new gamePoints cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GamePoints result = gamePointsRepository.save(gamePoints);
        return ResponseEntity.created(new URI("/api/game-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /game-points : Updates an existing gamePoints.
     *
     * @param gamePoints the gamePoints to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gamePoints,
     * or with status 400 (Bad Request) if the gamePoints is not valid,
     * or with status 500 (Internal Server Error) if the gamePoints couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/game-points")
    @Timed
    public ResponseEntity<GamePoints> updateGamePoints(@RequestBody GamePoints gamePoints) throws URISyntaxException {
        log.debug("REST request to update GamePoints : {}", gamePoints);
        if (gamePoints.getId() == null) {
            return createGamePoints(gamePoints);
        }
        GamePoints result = gamePointsRepository.save(gamePoints);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gamePoints.getId().toString()))
            .body(result);
    }

    /**
     * GET  /game-points : get all the gamePoints.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gamePoints in body
     */
    @GetMapping("/game-points")
    @Timed
    public List<GamePoints> getAllGamePoints() {
        log.debug("REST request to get all GamePoints");
        return gamePointsRepository.findAll();
        }

    /**
     * GET  /game-points/:id : get the "id" gamePoints.
     *
     * @param id the id of the gamePoints to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gamePoints, or with status 404 (Not Found)
     */
    @GetMapping("/game-points/{id}")
    @Timed
    public ResponseEntity<GamePoints> getGamePoints(@PathVariable Long id) {
        log.debug("REST request to get GamePoints : {}", id);
        GamePoints gamePoints = gamePointsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gamePoints));
    }

    /**
     * DELETE  /game-points/:id : delete the "id" gamePoints.
     *
     * @param id the id of the gamePoints to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/game-points/{id}")
    @Timed
    public ResponseEntity<Void> deleteGamePoints(@PathVariable Long id) {
        log.debug("REST request to delete GamePoints : {}", id);
        gamePointsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
