package de.felixhoevel.application.repository;

import de.felixhoevel.application.domain.Contestant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Contestant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContestantRepository extends JpaRepository<Contestant, Long> {

    Optional<Contestant> findOneByToken(String token);

}
