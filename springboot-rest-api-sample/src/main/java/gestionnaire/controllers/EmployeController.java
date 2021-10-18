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
            return new ResponseEntity<>(employes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> findEmployeById (@PathVariable Long id)
    {
        Optional<Employe> employe = employeRepository.findById(id);
        if (employe.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
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
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newEmploye, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Employe> updateEmploye (@RequestBody Employe employe)
    {
        Employe updatedEmploye;
        if (employeRepository.findById(employe.getId()).isEmpty()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            updatedEmploye = employeRepository.save(employe);
        } catch (Exception e){
            logger.error(e);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(updatedEmploye, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye (@PathVariable Long id)
    {
        Optional<Employe> employe = employeRepository.findById(id);
        if (employe.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        employeRepository.delete(employe.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
