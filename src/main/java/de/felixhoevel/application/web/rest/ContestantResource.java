package de.felixhoevel.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.felixhoevel.application.domain.Contestant;

import de.felixhoevel.application.domain.enumeration.Strength;
import de.felixhoevel.application.repository.ContestantRepository;
import de.felixhoevel.application.service.ContestantService;
import de.felixhoevel.application.service.MailService;
import de.felixhoevel.application.web.rest.errors.BadRequestAlertException;
import de.felixhoevel.application.web.rest.errors.InternalServerErrorException;
import de.felixhoevel.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.hibernate.id.GUIDGenerator;
import org.hibernate.id.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing Contestant.
 */
@RestController
@RequestMapping("/api")
public class ContestantResource {

    private final Logger log = LoggerFactory.getLogger(ContestantResource.class);

    private static final String ENTITY_NAME = "contestant";

    private final ContestantRepository contestantRepository;
    private final MailService mailService;
    private final ContestantService contestantService;



    public ContestantResource(ContestantRepository contestantRepository, MailService mailService, ContestantService contestantService) {
        this.contestantRepository = contestantRepository;
        this.mailService = mailService;
        this.contestantService = contestantService;
    }

    /**
     * POST  /contestants : Create a new contestant.
     *
     * @param contestant the contestant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contestant, or with status 400 (Bad Request) if the contestant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contestants")
    @Timed
    public ResponseEntity<Contestant> createContestant(@RequestBody Contestant contestant) throws URISyntaxException {
        log.debug("REST request to save Contestant : {}", contestant);
        if (contestant.getId() != null) {
            throw new BadRequestAlertException("A new contestant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contestant result = contestantRepository.save(contestant);
        return ResponseEntity.created(new URI("/api/contestants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contestants : Updates an existing contestant.
     *
     * @param contestant the contestant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contestant,
     * or with status 400 (Bad Request) if the contestant is not valid,
     * or with status 500 (Internal Server Error) if the contestant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contestants")
    @Timed
    public ResponseEntity<Contestant> updateContestant(@RequestBody Contestant contestant) throws URISyntaxException {
        log.debug("REST request to update Contestant : {}", contestant);
        if (contestant.getId() == null) {
            return createContestant(contestant);
        }
        Contestant result = contestantRepository.save(contestant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contestant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contestants : get all the contestants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contestants in body
     */
    @GetMapping("/contestants")
    @Timed
    public List<Contestant> getAllContestants() {
        log.debug("REST request to get all Contestants");
        return contestantRepository.findAll();
        }

    /**
     * GET  /contestants/:id : get the "id" contestant.
     *
     * @param id the id of the contestant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contestant, or with status 404 (Not Found)
     */
    @GetMapping("/contestants/{id}")
    @Timed
    public ResponseEntity<Contestant> getContestant(@PathVariable Long id) {
        log.debug("REST request to get Contestant : {}", id);
        Contestant contestant = contestantRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contestant));
    }

    /**
     * DELETE  /contestants/:id : delete the "id" contestant.
     *
     * @param id the id of the contestant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contestants/{id}")
    @Timed
    public ResponseEntity<Void> deleteContestant(@PathVariable Long id) {
        log.debug("REST request to delete Contestant : {}", id);
        contestantRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
