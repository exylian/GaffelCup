package de.felixhoevel.application.repository;

import de.felixhoevel.application.domain.Contestant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Contestant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContestantRepository extends JpaRepository<Contestant, Long> {

}
