package gestionnaire.service;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GestionnaireService {
    @Autowired
    ProjetRepository repo;

    @PostConstruct
    public void populate() {
        Projet projet = new Projet("Projet 1", "Description 1", new Employe());
    }
}
