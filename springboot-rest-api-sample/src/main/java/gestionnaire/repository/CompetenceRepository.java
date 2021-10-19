package gestionnaire.repository;

import gestionnaire.model.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
}
