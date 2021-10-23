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

@Controller
@RequestMapping("/api/projets")
public class ProjetController {

    @Autowired
    ProjetRepository repo;

    @Autowired
    ProjetService projetService;

    private final Log logger = LogFactory.getLog(getClass());


    @GetMapping()
    public ResponseEntity<List<Projet>> getAllProjet() {
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjet(@PathVariable long id) {
        return new ResponseEntity<>(repo.findById(id).get(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProjet(@PathVariable long id) {
        projetService.deleteProjetById(id);
    }

    @PostMapping()
    public Projet postProjet(@RequestBody Projet p) {
        repo.save(p);
        return p;
    }

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
