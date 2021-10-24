package gestionnaire.service;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service correspondant aux projets
 */

@Service
public class ProjetService {

    @Autowired
    ProjetRepository projetRepository;

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    TacheRepository tacheRepository;

    @Autowired
    EmployeService employeService;

    @Autowired
    TacheService tacheService;

    @Transactional
    public Projet findById(Long id){
        Optional<Projet> projet = projetRepository.findById(id);
        if (projet.isPresent()){
            Projet result = projet.get();
            result.getTaches().size();
            result.getEmployes().size();
            return result;
        }
        return null;
    }

    @Transactional
    public void deleteProjetById(Long id){
        Projet projet = projetRepository.findById(id).get();
        projet.setChef_projet(null);
        for (Tache tache:projet.getTaches()){
            tacheService.deleteTacheById(tache.getId());
        }
        for (Employe employe:projet.getEmployes()){
            employe.deleteProjet(projet);
            employeRepository.save(employe);
        }
        projet = projetRepository.save(projet);
        projetRepository.delete(projet);
    }

    @Transactional
    public void deleteAllProjet(){
        List<Projet> projets = projetRepository.findAll();
        for (Projet projet:projets){
            projet.setChef_projet(null);
            for (Tache tache:projet.getTaches()){
                tache.setProjet(null);
                tacheRepository.save(tache);
            }
            for (Employe employe:projet.getEmployes()){
                Employe e = employeService.findById(employe.getId());
                e.deleteProjet(projet);
                employeRepository.save(e);
            }
            projetRepository.save(projet);
        }
        projetRepository.deleteAll();
    }
}
