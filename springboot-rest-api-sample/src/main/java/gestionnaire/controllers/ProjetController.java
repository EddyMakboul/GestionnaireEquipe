package gestionnaire.controllers;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/projets")
public class ProjetController {

    @Autowired
    ProjetRepository repo;

    @GetMapping()
    public ResponseEntity<List<Projet>> getAllProjet() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjet(@PathVariable long id) {
        return new ResponseEntity<>(repo.findById(id).get(),HttpStatus.OK);
    }

    @DeleteMapping("/projet/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProjet(@PathVariable long id) {
        repo.deleteById(id);
    }

    @PostMapping("/projet")
    public Projet postProjet(@RequestBody Projet p) {
        repo.save(p);
        return p;
    }

}
