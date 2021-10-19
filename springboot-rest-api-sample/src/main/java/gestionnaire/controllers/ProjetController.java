package gestionnaire.controllers;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class ProjetController {

    @Autowired
    ProjetRepository repo;

    @GetMapping("/projet")
    public Iterable<Projet> getAllProjet() {
        return repo.findAll();
    }

    @GetMapping("/projet/{id}")
    public Projet getProjet(@PathVariable long id) {
        return repo.findById(id).get();
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
