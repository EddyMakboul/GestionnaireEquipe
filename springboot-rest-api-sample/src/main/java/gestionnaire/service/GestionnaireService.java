package gestionnaire.service;

import com.github.javafaker.Faker;
import gestionnaire.model.Competence;
import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Role;
import gestionnaire.repository.CompetenceRepository;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class GestionnaireService {
    @Autowired
    ProjetRepository projetRepository;

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CompetenceRepository competenceRepository;

    @PostConstruct
    public void populate() {
        Role developpeurRole = new Role("developpeur");
        Role ChefDeProjetRole = new Role("Chef de projet");
        roleRepository.save(developpeurRole);
        roleRepository.save(ChefDeProjetRole);

        Competence competenceJava = new Competence("Java", "Bon");
        Competence competenceGP = new Competence("Gestion de projet", "Expert");
        Competence competenceScrum = new Competence("Scrum", "Moyen");
        Competence competenceC = new Competence("C++", "Bon");
        competenceRepository.save(competenceJava);
        competenceRepository.save(competenceGP);
        competenceRepository.save(competenceScrum);
        competenceRepository.save(competenceC);


        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            Employe employe = new Employe(faker.name().firstName(), faker.name().lastName(), faker.name().username(), "password", developpeurRole);
            employe.addCompetence(competenceJava);
            employeRepository.save(employe);
        }
        for (int i = 0; i < 5; i++) {
            Faker faker = new Faker();
            Employe employe = new Employe(faker.name().firstName(), faker.name().lastName(), faker.name().username(), "password", ChefDeProjetRole);
            employeRepository.save(employe);
        }

        List<Employe> chefs = employeRepository.findAllChefDeProjet();
        for (int i = 0; i < 5 ; i++) {
            Projet projet = new Projet("Projet " + i, "Description " + i, chefs.get(i));
            projetRepository.save(projet);
        }
    }
}
