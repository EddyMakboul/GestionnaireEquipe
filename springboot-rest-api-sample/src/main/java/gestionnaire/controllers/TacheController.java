package gestionnaire.controllers;

import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.TacheRepository;
import gestionnaire.service.TacheService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller pour les Taches, permet de gérer les actions sur les taches
 */
@RestController
@RequestMapping(value = "/api/taches")
public class TacheController {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    TacheRepository tacheRepository;

    @Autowired
    TacheService tacheService;


    @Autowired
    ProjetRepository projetRepository;

    /**
     * Methode Post, permet de créer une nouvelle tache
     * @param tache une Tache
     * @return
     */
    @PostMapping()
    public ResponseEntity<Tache> createTask(@RequestBody Tache tache){
        tacheRepository.save(tache);
        return new ResponseEntity<Tache>(tache,HttpStatus.CREATED);
    }

    /**
     * Methode DELETE, permet de supprimer une tache via son id
     * @param id l'id d'une Tache
     */
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id){
        tacheService.deleteTacheById(id);
    }

    /**
     * Methode PUT, permet de mettre à jour une tache
     * @param task une Tache
     * @return
     */
    @PutMapping()
    public ResponseEntity<Tache> updateTask (@RequestBody Tache task){
        Tache updateTask;
        if (tacheRepository.findById(task.getId()).isEmpty()){
            logger.error("Can't update task. Task with id = "+task.getId()+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            updateTask = tacheRepository.save(task);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update task with id = "+task.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated task");
        return new ResponseEntity<>(updateTask, HttpStatus.CREATED);
    }

    /**
     * Methode GET, permet de récupérer une liste de taches d'un projet
     * @param id l'id d'un Projet
     * @return
     */
    @GetMapping("/projet/{id}")
    public ResponseEntity<List<Tache>> getTastFromProject(@PathVariable Long id){
        Optional<Projet> projet = projetRepository.findById(id);
        if (projet.isEmpty()){
            logger.error("Can't find projet. Projet with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tacheRepository.findTacheByProjet(projet.get()),HttpStatus.OK);
    }

    /**
     * Methode GET, permet de récupérer une tache via son id
     * @param id l'id d'une Tache
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tache> findTacheById (@PathVariable Long id)
    {
        Optional<Tache> tache = tacheRepository.findById(id);
        if (tache.isEmpty()){
            logger.error("Can't find tache. Tache with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Return tache id = "+id);
        return new ResponseEntity<>(tache.get(), HttpStatus.OK);
    }

    /**
     * Methode PUT, permet de mettre à jour une Tache
     * @param id l'id d'une Tache
     * @param finished un booleen
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateStatusTache	 (@PathVariable Long id,@RequestBody Boolean finished)
    {
        Optional<Tache> tache = tacheRepository.findById(id);
        if (tache.isEmpty()){
            logger.error("Can't find tache. Tache with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Tache updatedTache;
        Tache tache1 = tache.get();
        tache1.setFinished(finished);
        try {
            updatedTache = tacheRepository.save(tache1);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of tache with id = "+tache1.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return tache id = "+id);
        return new ResponseEntity<>(updatedTache, HttpStatus.OK);
    }
    

}
