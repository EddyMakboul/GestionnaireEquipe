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
