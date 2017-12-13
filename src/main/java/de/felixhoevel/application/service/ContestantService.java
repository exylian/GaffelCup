package de.felixhoevel.application.service;


import de.felixhoevel.application.domain.Contestant;
import de.felixhoevel.application.repository.ContestantRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@Transactional
public class ContestantService {

    private final Logger log = LoggerFactory.getLogger(ContestantService.class);

    private final ContestantRepository contestantRepository;

    public ContestantService(ContestantRepository contestantRepository) {
        this.contestantRepository = contestantRepository;
    }

    public Optional<Contestant> confirmRegistration(String token) {
        log.debug("Confirming contestant with token {}", token);
        return contestantRepository.findOneByToken(token)
            .map(contestant -> {
                // activate given user for the registration key.
                contestant.setConfirmed(true);
                contestant.setConfirmedAt(ZonedDateTime.now());

                log.debug("Confirmed contestant: {}", contestant);
                return contestant;
            });
    }

}
