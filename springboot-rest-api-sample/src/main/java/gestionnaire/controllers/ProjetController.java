package gestionnaire.controllers;

import gestionnaire.model.Projet;
import gestionnaire.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
