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

    @GetMapping()
    public Iterable<Tache> getAllActiveTasks(){
        return tacheRepository.findAll();
    }

    @PostMapping()
    public Tache createTask(@RequestBody Tache tache){
        tacheRepository.save(tache);
        return tache;
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id){
        tacheService.deleteTacheById(id);
    }

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

    @GetMapping("/projet/{id}")
    public ResponseEntity<List<Tache>> getTastFromProject(@PathVariable Long id){
        Optional<Projet> projet = projetRepository.findById(id);
        if (projet.isEmpty()){
            logger.error("Can't find projet. Projet with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tacheRepository.findTacheByProjet(projet.get()),HttpStatus.OK);
    }

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
