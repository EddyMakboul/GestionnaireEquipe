package gestionnaire.controllers;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.service.ProjetService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller pour les projets, permet la gestion des projets
 */
@Controller
@RequestMapping("/api/projets")
public class ProjetController {

    @Autowired
    ProjetRepository repo;

    @Autowired
    ProjetService projetService;

    private final Log logger = LogFactory.getLog(getClass());


    /**
     * Methode GET, permet de récupérer tous les projets
     * @return new responseEntity avec le status de la requête http
     */
    @GetMapping()
    public ResponseEntity<List<Projet>> getAllProjet() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    /**
     * Methode GET, permet de récupérer un projet via son id
     * @param id l'id du projet
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjet(@PathVariable long id) {
        return new ResponseEntity<>(repo.findById(id).get(),HttpStatus.OK);
    }

    /**
     * Methode DELETE, permet la suppression d'un projet
     * @param id l'id du projet
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProjet(@PathVariable long id) {
        projetService.deleteProjetById(id);
    }

    /**
     * Methode POST, pemrmet la création d'un nouveau projet
     * @param p un Projet
     * @return
     */
    @PostMapping()
    public ResponseEntity<Projet> postProjet(@RequestBody Projet p) {
        Projet n =  repo.save(p);
        return new ResponseEntity<>(n,HttpStatus.OK);
    }

    /**
     * Methode PUT, permet de mettre à jour un projet
     * @param projet un Projet
     * @return
     */
    @PutMapping()
    public ResponseEntity<Projet> updateProjet (@RequestBody Projet projet)
    {
        Projet updatedProjet;
        if (repo.findById(projet.getId()).isEmpty()){
            logger.error("Can't update projet. Projet with id = "+projet.getId()+" doesn't exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        try {
            updatedProjet = repo.save(projet);
        } catch (Exception e){
            logger.error(e);
            logger.error("Error during update of projet with id = "+projet.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Return updated projet");
        return new ResponseEntity<>(updatedProjet, HttpStatus.OK);
    }
}
