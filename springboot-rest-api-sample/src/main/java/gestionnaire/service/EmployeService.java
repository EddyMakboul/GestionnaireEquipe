package gestionnaire.service;

import gestionnaire.model.Competence;
import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeService {
    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    ProjetRepository projetRepository;

    @Autowired
    TacheRepository tacheRepository;

    public void deleteAllEmployee(){
        List<Employe> employees = employeRepository.findAll();
        Projet projet;
        for (Employe employe:employees){
            projet = projetRepository.findByProjetByChef(employe);
            if (projet != null){
                projet.setChef_projet(null);
                projetRepository.save(projet);
            }
            employe.setTaches(new ArrayList<>());
            deleteEmployeeTache(employe);
            employe.setProjets(new ArrayList<>());
            employe.setCompetences(new ArrayList<>());
            employeRepository.save(employe);
        }
        employeRepository.deleteAll();
    }

    public void deleteEmployeById(Long id){
        Employe employe = employeRepository.findById(id).get();
        Projet projet;
        projet = projetRepository.findByProjetByChef(employe);
        if (projet != null){
            projet.setChef_projet(null);
            projetRepository.save(projet);
        }
        employe.setTaches(new ArrayList<>());
        deleteEmployeeTache(employe);
        employe.setProjets(new ArrayList<>());
        employe.setCompetences(new ArrayList<>());
        employeRepository.save(employe);
        employeRepository.delete(employe);
    }

    @Transactional
    public List<Employe> findByNomAndPrenom(String nom, String prenom){
        List<Employe> employes = employeRepository.findByNomAndPrenom(nom, prenom);
        for (Employe employe: employes){
            employe.getCompetences().size();
            employe.getTaches().size();
            employe.getProjets().size();
        }
        return employes;
    }

    @Transactional
    public Employe findById(Long id){
        Optional<Employe> employe = employeRepository.findById(id);
        if (employe.isPresent()){
            Employe result = employe.get();
            result.getCompetences().size();
            result.getTaches().size();
            result.getProjets().size();
            return result;
        }
        return null;
    }

    @Transactional
    public boolean employeHasCompetence(Long id, Long id_c){
        Optional<Employe> employe = employeRepository.findById(id);
        Employe result = employe.get();
        result.getCompetences().size();
        result.getTaches().size();
        result.getProjets().size();
        for (Competence competence:result.getCompetences()){
            if (competence.getId() == id_c){
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean employeHasProjet(Long id, Long id_p){
        Optional<Employe> employe = employeRepository.findById(id);
        Employe result = employe.get();
        result.getCompetences().size();
        result.getTaches().size();
        result.getProjets().size();
        for (Projet projet:result.getProjets()){
            if (projet.getId() == id_p){
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean employeHasTache(Long id, Long id_t){
        Optional<Employe> employe = employeRepository.findById(id);
        Employe result = employe.get();
        result.getCompetences().size();
        result.getTaches().size();
        result.getProjets().size();
        for (Tache tache:result.getTaches()){
            if (tache.getId() == id_t){
                return true;
            }
        }
        return false;
    }

    private void deleteEmployeeTache(Employe employe){
        for (Tache t: tacheRepository.findTacheByEmploye(employe)){
            t.setEmploye(null);
            tacheRepository.save(t);
        }
    }
}
