package gestionnaire.repository;

import gestionnaire.model.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Tache, Long> {

}
