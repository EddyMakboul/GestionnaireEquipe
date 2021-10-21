package gestionnaire.service;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    ProjetRepository projetRepository;

    public void deleteAllEmployee(){
        List<Employe> employees = employeRepository.findAll();
        Projet projet;
        for (Employe employe:employees){
            projet = projetRepository.findByProjetByChef(employe);
            if (projet != null){
                projet.setChef_projet(null);
            }
            employe.setCompetences(new ArrayList<>());
            employe.setProjets(new ArrayList<>());
            employe.setTaches(new ArrayList<>());
            employeRepository.save(employe);
        }
        employeRepository.deleteAll();
    }
}
