package gestionnaire.service;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TacheService {

    @Autowired
    TaskRepository taskRepository;

    public void deleteTacheById(Long id){
        Optional<Tache> potentialTache = taskRepository.findById(id);
        if (potentialTache.isPresent()){
            Tache tache = potentialTache.get();
            tache.setEmploye(null);
            tache.setProjet(null);
            taskRepository.save(tache);
            taskRepository.deleteById(id);
        }
    }
}
