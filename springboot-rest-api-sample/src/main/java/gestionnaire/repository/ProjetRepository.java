package gestionnaire.repository;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProjetRepository extends JpaRepository<Projet, Long> {
    @Query("SELECT p FROM Projet AS p WHERE p.chef_projet = :employe")
    Projet findByProjetByChef(Employe employe);
}
