package gestionnaire.controllers;

import gestionnaire.model.Competence;
import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.CompetenceRepository;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.TacheRepository;
import gestionnaire.service.EmployeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller pour les employés, permet de gérer les actions pour les employés
 */

@Controller()
@RequestMapping("/api/employes")
public class EmployeController {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    CompetenceRepository competenceRepository;

    @Autowired
    ProjetRepository projetRepository;

    @Autowired
    TacheRepository tacheRepository;

    /**
     * methode GET, permet de récupérer tous les employés
     * @return
     */
    @GetMapping()
    public ResponseEntity<List<Employe>> findAllEmployes ()
    {
        List<Employe> employes = employeRepository.findAll();
        if (!employes.isEmpty()) {
        	 logger.info("Return list of employes");
            return new ResponseEntity<>(employes, HttpStatus.OK);
        }
        logger.error("There is no employes");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     *Methode GET, permettant de recuperer un employé via son id
     * @param id l'id de l'employé
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employe> findEmployeById (@PathVariable Long id)
    {
        Optional<Employe> employe = employeRepository.findById(id);
        if (employe.isEmpty()){
            logger.error("Can't find employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Return employe id = "+id);
        return new ResponseEntity<>(employe.get(), HttpStatus.OK);
    }

    /**
     * Methode POST, permettant la création d'un nouvel employé
     * @param employe un Employe
     * @return
     */
    @PostMapping()
    public ResponseEntity<Employe> createEmploye (@RequestBody Employe employe)
    {
        Employe newEmploye;
        try {
            newEmploye = employeRepository.save(employe);
        } catch (Exception e){
            logger.error(e);
            logger.error("Invalid Data for creation of employe");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return new employe");
        return new ResponseEntity<>(newEmploye, HttpStatus.CREATED);
    }

    /**
     * Methode PUT, permettant de mettre à jour un employe
     * @param employe un Employe
     * @return
     */
    @PutMapping()
    public ResponseEntity<Employe> updateEmploye (@RequestBody Employe employe)
    {
        Employe updatedEmploye;
        if (employeRepository.findById(employe.getId()).isEmpty()){
            logger.error("Can't update employe. Employe with id = "+employe.getId()+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            updatedEmploye = employeRepository.save(employe);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of employee with id = "+employe.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated employe");
        return new ResponseEntity<>(updatedEmploye, HttpStatus.OK);
    }

    /**
     * Methode DELETE, permet de supprimet un employé via son id
     * @param id l'id d'un Employe
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye (@PathVariable Long id)
    {
        Optional<Employe> employe = employeRepository.findById(id);
        if (employe.isEmpty()){
            logger.error("Can't delete employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        try {
        	employeService.deleteEmployeById(id);
        } catch (Exception e) {
        	logger.error(e);
        	return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
        
        logger.info("Employe with id = "+id+" deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Methode PUT, mise à jour d'un employé en lui ajoutant une compétence
     * @param competence une Compétence
     * @param id d'un Employe
     * @return
     */
    @PutMapping("/addcompetence/{id}")
    public ResponseEntity<Employe> updateEmployeAddCompetence (@RequestBody Competence competence, @PathVariable Long id)
    {
        Optional<Employe> potentialEmploye = employeRepository.findById(id);
        if (potentialEmploye.isEmpty()){
            logger.error("Can't add competence to employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (competenceRepository.findById(competence.getId()).isEmpty()){
            logger.error("Can't add competence to employe. Competence with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Employe updatedEmploye;
        Employe employe = potentialEmploye.get();
        employe.addCompetence(competence);
        try {
            updatedEmploye = employeRepository.save(employe);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of employee with id = "+employe.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated employe");
        return new ResponseEntity<>(updatedEmploye, HttpStatus.OK);
    }


    /**
     * Methode PUT, mise à jour d'un employé en lui supprimant une compétences
     * @param competence une Competence
     * @param id d'un Employe
     * @return
     */
    @PutMapping("/removecompetence/{id}")
    public ResponseEntity<Employe> updateEmployeRemoveCompetence (@RequestBody Competence competence, @PathVariable Long id)
    {
        Optional<Employe> potentialEmploye = employeRepository.findById(id);
        if (potentialEmploye.isEmpty()){
            logger.error("Can't remove competence to employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (competenceRepository.findById(competence.getId()).isEmpty()){
            logger.error("Can't remove competence to employe. Competence with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (!employeService.employeHasCompetence(id, competence.getId())){
            logger.error("Can't remove competence to employe. Employe with id = "+id+" doesn't have " +
                    "competence with id = "+competence.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Employe updatedEmploye;
        Employe employe = potentialEmploye.get();
        employe.deleteCompetence(competence);
        try {
            updatedEmploye = employeRepository.save(employe);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of employee with id = "+employe.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated employe");
        return new ResponseEntity<>(updatedEmploye, HttpStatus.OK);
    }

    /**
     * Methode PUT, pemet d'affecter un employe à un projet
     * @param projet un Projet
     * @param id d'un Employe
     * @return
     */
    @PutMapping("/addprojet/{id}")
    public ResponseEntity<Employe> updateEmployeAddProjet (@RequestBody Projet projet, @PathVariable Long id)
    {
        Optional<Employe> potentialEmploye = employeRepository.findById(id);
        if (potentialEmploye.isEmpty()){
            logger.error("Can't add projet to employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (projetRepository.findById(projet.getId()).isEmpty()){
            logger.error("Can't add projet to employe. Projet with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Employe updatedEmploye;
        Employe employe = potentialEmploye.get();
        employe.addProjet(projet);
        try {
            updatedEmploye = employeRepository.save(employe);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of employee with id = "+employe.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated employe");
        return new ResponseEntity<>(updatedEmploye, HttpStatus.OK);
    }


    /**
     * Methode PUT, permet de retirer un employé à un projet
     * @param projet un Projet
     * @param id d'un Employé
     * @return
     */
    @PutMapping("/removeprojet/{id}")
    public ResponseEntity<Employe> updateEmployeRemoveProjet (@RequestBody Projet projet, @PathVariable Long id)
    {
        Optional<Employe> potentialEmploye = employeRepository.findById(id);
        if (potentialEmploye.isEmpty()){
            logger.error("Can't remove projet to employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (projetRepository.findById(projet.getId()).isEmpty()){
            logger.error("Can't remove projet to employe. Projet with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (!employeService.employeHasProjet(id, projet.getId())){
            logger.error("Can't remove projet to employe. Employe with id = "+id+" doesn't have " +
                    "projet with id = "+projet.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Employe updatedEmploye;
        Employe employe = potentialEmploye.get();
        employe.deleteProjet(projet);
        try {
            updatedEmploye = employeRepository.save(employe);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of employee with id = "+employe.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated employe");
        return new ResponseEntity<>(updatedEmploye, HttpStatus.OK);
    }

    /**
     * Method PUT, permet d'ajouter une tache à un employé qui est affecté à un projet
     * @param tache une Tache
     * @param id d'un Employé
     * @return
     */
    @PutMapping("/addtache/{id}")
    public ResponseEntity<Employe> updateEmployeAddProjet (@RequestBody Tache tache, @PathVariable Long id)
    {
        Optional<Employe> potentialEmploye = employeRepository.findById(id);
        if (potentialEmploye.isEmpty()){
            logger.error("Can't add tache to employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (tacheRepository.findById(tache.getId()).isEmpty()){
            logger.error("Can't add tache to employe. Tache with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Employe updatedEmploye;
        Employe employe = potentialEmploye.get();
        tache.setEmploye(employe);
        tacheRepository.save(tache);
        try {
            updatedEmploye = employeRepository.findById(id).get();
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of employee with id = "+employe.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated employe");
        return new ResponseEntity<>(updatedEmploye, HttpStatus.OK);
    }

    /**
     * Methode PUT, permet de retirer une tache à un employé
     * @param tache une Tache
     * @param id d'un Employe
     * @return
     */
    @PutMapping("/removetache/{id}")
    public ResponseEntity<Employe> updateEmployeRemoveProjet (@RequestBody Tache tache, @PathVariable Long id)
    {
        Optional<Employe> potentialEmploye = employeRepository.findById(id);
        if (potentialEmploye.isEmpty()){
            logger.error("Can't remove tache to employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (projetRepository.findById(tache.getId()).isEmpty()){
            logger.error("Can't remove tache to employe. Tache with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (!employeService.employeHasTache(id, tache.getId())){
            logger.error("Can't remove tache to employe. Employe with id = "+id+" doesn't have " +
                    "tache with id = "+tache.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Employe updatedEmploye;
        Employe employe = potentialEmploye.get();
        tache.setEmploye(null);
        tacheRepository.save(tache);
        try {
            updatedEmploye = employeRepository.findById(id).get();
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of employee with id = "+employe.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated employe");
        return new ResponseEntity<>(updatedEmploye, HttpStatus.OK);
    }

    /**
     * Methode GET, permet de récuperer tous les chefs de projet
     * @return
     */
    @GetMapping("/chef")
    public ResponseEntity<List<Employe>> findAllchef ()
    {
        List<Employe> employes = employeRepository.findAllChefDeProjet();
        if (!employes.isEmpty()) {
            logger.info("Return list of chef");
            return new ResponseEntity<>(employes, HttpStatus.OK);
        }
        logger.error("There is no chef");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
