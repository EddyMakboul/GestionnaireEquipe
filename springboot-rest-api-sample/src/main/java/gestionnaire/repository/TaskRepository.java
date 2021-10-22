package gestionnaire.repository;

import gestionnaire.model.Employe;
import gestionnaire.model.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tache, Long> {
    List<Tache> findTacheByEmploye(Employe employe);
}
