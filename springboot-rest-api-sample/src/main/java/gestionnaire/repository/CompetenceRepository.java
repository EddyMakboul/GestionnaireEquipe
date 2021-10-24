package gestionnaire.repository;

import gestionnaire.model.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository permettant les operations sur les competences
 */

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
}
