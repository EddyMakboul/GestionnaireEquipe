package gestionnaire.repository;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository permettant les opérations sur les Taches
 */

public interface TacheRepository extends JpaRepository<Tache, Long> {
    List<Tache> findTacheByEmploye(Employe employe);

    List<Tache> findTacheByProjet(Projet projet);
}
