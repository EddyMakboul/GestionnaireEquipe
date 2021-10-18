package gestionnaire.controllers;

import gestionnaire.model.Employe;
import gestionnaire.repository.EmployeRepository;
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
    EmployeRepository employeRepository;

    @GetMapping()
    public ResponseEntity<List<Employe>> findAllEmployes ()
    {
        List<Employe> employes = employeRepository.findAll();
        if (!employes.isEmpty()) {
            logger.error("There is no employes");
            return new ResponseEntity<>(employes, HttpStatus.OK);
        }
        logger.info("Return list of employes");
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
        return new ResponseEntity<>(updatedEmploye, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye (@PathVariable Long id)
    {
        Optional<Employe> employe = employeRepository.findById(id);
        if (employe.isEmpty()){
            logger.error("Can't delete employe. Employe with id = "+id+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        employeRepository.delete(employe.get());
        logger.info("Employe with id = "+id+" deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
