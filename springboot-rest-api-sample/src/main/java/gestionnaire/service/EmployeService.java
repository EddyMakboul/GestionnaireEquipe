package gestionnaire.service;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeService {
    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    ProjetRepository projetRepository;

    @Autowired
    TaskRepository taskRepository;

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

    private void deleteEmployeeTache(Employe employe){
        for (Tache t: taskRepository.findTacheByEmploye(employe)){
            t.setEmploye(null);
            taskRepository.save(t);
        }
    }
}