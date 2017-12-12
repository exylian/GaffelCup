package de.felixhoevel.application.repository;

import de.felixhoevel.application.domain.GamePoints;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GamePoints entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamePointsRepository extends JpaRepository<GamePoints, Long> {

}
