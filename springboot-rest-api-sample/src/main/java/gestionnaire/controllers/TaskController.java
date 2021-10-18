package gestionnaire.controllers;

import gestionnaire.model.Tache;
import gestionnaire.repository.TaskRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/task")
public class TaskController {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/list")
    public Iterable<Tache> getAllActiveTasks(){
        return taskRepository.findAll();
    }

    @PostMapping("/create")
    public Tache createTask(@RequestBody Tache task){
        taskRepository.save(task);
        return task;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable long id){
        taskRepository.deleteById(id);
    }

    @PutMapping("update")
    public ResponseEntity<Tache> updateEmploye (@RequestBody Tache task){
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


}
