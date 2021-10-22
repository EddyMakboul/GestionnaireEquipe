package gestionnaire.controllers;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.TaskRepository;
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
public class TaskController {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjetRepository projetRepository;

    @GetMapping()
    public Iterable<Tache> getAllActiveTasks(){
        return taskRepository.findAll();
    }

    @PostMapping()
    public Tache createTask(@RequestBody Tache tache){
        taskRepository.save(tache);
        return tache;
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id){
        taskRepository.deleteById(id);
    }

    @PutMapping()
    public ResponseEntity<Tache> updateTask (@RequestBody Tache task){
        Tache updateTask;
        if (taskRepository.findById(task.getId()).isEmpty()){
            logger.error("Can't update task. Task with id = "+task.getId()+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            updateTask = taskRepository.save(task);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update task with id = "+task.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated task");
        return new ResponseEntity<>(updateTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Tache>> getTastFromProject(@PathVariable Long id){
        Optional<Projet> projet = projetRepository.findById(id);
        if (projet.isEmpty()){
            logger.error("Can't find projet. Projet with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(taskRepository.findTacheByProjet(projet.get()),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tache> findTacheById (@PathVariable Long id)
    {
        Optional<Tache> tache = taskRepository.findById(id);
        if (tache.isEmpty()){
            logger.error("Can't find tache. Tache with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Return tache id = "+id);
        return new ResponseEntity<>(tache.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tache> tacheFinis (@PathVariable Long id)
    {
        Optional<Tache> tache = taskRepository.findById(id);
        if (tache.isEmpty()){
            logger.error("Can't find tache. Tache with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Tache updatedTache;
        Tache tache1 = tache.get();
        tache1.setFinished(true);
        try {
            updatedTache = taskRepository.save(tache1);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of tache with id = "+tache1.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return tache id = "+id);
        return new ResponseEntity<>(updatedTache, HttpStatus.OK);
    }

}
