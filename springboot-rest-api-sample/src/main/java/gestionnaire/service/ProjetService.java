package gestionnaire.service;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProjetService {

    @Autowired
    ProjetRepository projetRepository;

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
}
