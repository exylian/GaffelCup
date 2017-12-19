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
import org.checkerframework.checker.units.qual.Time;
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

@RestController
@RequestMapping("api/public")
public class PublicContestantController {

    private static final String ENTITY_NAME = "contestant";


    private final Logger log = LoggerFactory.getLogger(PublicContestantController.class);
    private final ContestantRepository contestantRepository;
    private final MailService mailService;
    private ContestantService contestantService;


    /**
     * Default Constructor
     * @param contestantRepository
     * @param mailService
     * @param contestantService
     */
    public PublicContestantController(ContestantRepository contestantRepository, MailService mailService, ContestantService contestantService) {
        this.contestantRepository = contestantRepository;
        this.mailService = mailService;
        this.contestantService = contestantService;
    }

    /**
     *  REGISTER Contestant
     * @param contestant
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/contestants/register")
    @Timed
    public ResponseEntity<Contestant> registerContestant(@RequestBody Contestant contestant) throws URISyntaxException {
        log.debug("REST request to save Contestant : {}", contestant);
        if (contestant.getId() != null) {
            throw new BadRequestAlertException("A new contestant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<Contestant> all = contestantRepository.findAll();
        for(Iterator<Contestant> i = all.iterator(); i.hasNext();){
            Contestant test = i.next();
            if (test.geteMail().equalsIgnoreCase(contestant.geteMail())){
                throw new BadRequestAlertException("Eine Anmeldung mit dieser E-Mail existiert bereits!", ENTITY_NAME, "emailexists");
            }
        }

        contestant.setToken(UUID.randomUUID().toString());
        contestant.setConfirmed(false);
        contestant.setPayed(false);
        contestant.setStrength(Strength.MID);
        contestant.setTotalPoints(0);
        Contestant result = contestantRepository.save(contestant);
        mailService.sendRegistrationMail(contestant);
        return ResponseEntity.created(new URI("/api/contestants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contestants/confirm : confirm the created contestant
     *  When success generate new Token
     * @param key the activation key
     * @throws RuntimeException 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/contestants/confirm")
    @Timed
    public void confirmContestant(@RequestParam(value = "key") String key) {
        Optional<Contestant> contestant = contestantService.confirmRegistration(key);
        if (!contestant.isPresent()) {
            throw new InternalServerErrorException("No contestant was found for this reset key");
        }
        log.debug("Set new Token for contestant {}", contestant);
        contestant.get().setToken(UUID.randomUUID().toString());
        contestantRepository.save(contestant.get());
        mailService.sendConfirmationMail(contestant.get());
    }

    /**
     * GET /contestants/unconfirm : unconfirm a contestant
     * @param key
     */
    @GetMapping("/contestants/unconfirm")
    @Timed
    public void unconfirmContestant(@RequestParam(value = "key") String key){
        Optional<Contestant> contestant = contestantService.unconfirmContestant(key);
        if (!contestant.isPresent()) {
            throw new InternalServerErrorException("No contestant was found for this reset key");
        }
        mailService.sendDeletionMail(contestant.get());
    }


}
