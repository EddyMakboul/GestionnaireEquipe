package gestionnaire.repository;

import gestionnaire.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

    List<Employe> findByNomAndPrenom(String name,String firstname);

    @Query("SELECT e FROM Employe AS e WHERE e.role.nom_role = 'Chef de projet'")
    List<Employe> findAllChefDeProjet();
}
