package gestionnaire.service;

import gestionnaire.model.Tache;
import gestionnaire.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TacheService {

    @Autowired
    TacheRepository tacheRepository;

    public void deleteTacheById(Long id){
        Optional<Tache> potentialTache = tacheRepository.findById(id);
        if (potentialTache.isPresent()){
            Tache tache = potentialTache.get();
            tache.setEmploye(null);
            tache.setProjet(null);
            tacheRepository.save(tache);
            tacheRepository.deleteById(id);
        }
    }

    public void deleteAllTask(){
        List<Tache> taches = tacheRepository.findAll();
        for (Tache tache: taches){
            tache.setEmploye(null);
            tache.setProjet(null);
            tacheRepository.save(tache);
        }
        tacheRepository.deleteAll();
    }
}
