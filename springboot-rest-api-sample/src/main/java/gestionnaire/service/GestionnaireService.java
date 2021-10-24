package gestionnaire.service;

import com.github.javafaker.Faker;
import gestionnaire.model.*;
import gestionnaire.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
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

    @Autowired
    TacheRepository tacheRepository;

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
        Competence competenceSpring = new Competence("Spring", "Expert");
        competenceRepository.save(competenceJava);
        competenceRepository.save(competenceGP);
        competenceRepository.save(competenceScrum);
        competenceRepository.save(competenceC);
        competenceRepository.save(competenceSpring);

        Employe chef1 = populateEmploye(Arrays.asList(competenceScrum, competenceGP), null, ChefDeProjetRole);
        Employe chef2 = populateEmploye(Arrays.asList(competenceScrum, competenceGP), null, ChefDeProjetRole);
        Employe chef3 = populateEmploye(Arrays.asList(competenceScrum, competenceGP), null, ChefDeProjetRole);
        Employe chef4 = populateEmploye(Arrays.asList(competenceScrum, competenceGP), null, ChefDeProjetRole);
        Employe chef5 = populateEmploye(Arrays.asList(competenceScrum, competenceGP), null, ChefDeProjetRole);

        Projet projet1 = projetRepository.save(new Projet("Projet 1", "Description 1", chef1));
        Projet projet2 = projetRepository.save(new Projet("Projet 2", "Description 2", chef2));
        Projet projet3 = projetRepository.save(new Projet("Projet 3", "Description 3", chef3));
        Projet projet4 = projetRepository.save(new Projet("Projet 4", "Description 4", chef4));
        Projet projet5 = projetRepository.save(new Projet("Projet 5", "Description 5", chef5));

        Employe employe1 = populateEmploye(Arrays.asList(competenceC, competenceGP), Arrays.asList(projet1, projet5), developpeurRole);
        Employe employe2 = populateEmploye(List.of(competenceJava), Arrays.asList(projet1, projet4), developpeurRole);
        Employe employe3 = populateEmploye(Arrays.asList(competenceC, competenceGP, competenceScrum), Arrays.asList(projet1), developpeurRole);
        Employe employe4 = populateEmploye(Arrays.asList(competenceSpring, competenceJava),Arrays.asList(projet2, projet5), developpeurRole);
        Employe employe5 = populateEmploye(Arrays.asList(competenceScrum, competenceGP),Arrays.asList(projet2, projet4), developpeurRole);
        Employe employe6 = populateEmploye(Arrays.asList(competenceC, competenceSpring),Arrays.asList(projet2), developpeurRole);
        Employe employe7 = populateEmploye(List.of(competenceGP),Arrays.asList(projet3), developpeurRole);
        Employe employe8 = populateEmploye(Arrays.asList(competenceC, competenceGP, competenceJava, competenceSpring), Arrays.asList(projet3), developpeurRole);
        Employe employe9 = populateEmploye(Arrays.asList(competenceJava, competenceScrum),Arrays.asList(projet3, projet5), developpeurRole);
        Employe employe10 = populateEmploye(Arrays.asList(competenceScrum, competenceGP, competenceJava),Arrays.asList(projet4), developpeurRole);

        tacheRepository.save(new Tache("Faire les controllers", "Controller", employe1, projet1));
        tacheRepository.save(new Tache("Faire les services", "Services", employe2, projet1));
        tacheRepository.save(new Tache("Faire les beans", "beans", employe3, projet1));
        tacheRepository.save(new Tache("Faire l'UML", "UML", employe4, projet2));
        tacheRepository.save(new Tache("Faire le MCD", "MCD", employe5, projet2));
        tacheRepository.save(new Tache("Faire le login", "Login", employe6, projet2));
        tacheRepository.save(new Tache("Permettre l'ajout de personnes", "Ajout de personnes", employe7, projet3));
        tacheRepository.save(new Tache("Permettre la suppression de personnes", "Suppression de personnes", employe8, projet3));
        tacheRepository.save(new Tache("Faire le front de l'accueil", "Front de l'accueil", employe9, projet3));
        tacheRepository.save(new Tache("Faire le front du back office", "Font du back office", employe10, projet4));
        tacheRepository.save(new Tache("Peupler la BD", "Peuplement de la Base de donées", employe2, projet4));
        tacheRepository.save(new Tache("Ecrire la specification", "Rediger la specification technique", employe5, projet4));
        tacheRepository.save(new Tache("Mettre en place la BD", "Mis en place de la base de données", employe1, projet5));
        tacheRepository.save(new Tache("Tester les services", "Test unitaire des services", employe4, projet5));
        tacheRepository.save(new Tache("Faire le front des infos des users", "Front des informations sur les utilisateurs", employe9, projet5));
    }

    private Employe populateEmploye(List<Competence> competences, List<Projet> projets, Role role) {
        Faker faker = new Faker();
        Employe employe = new Employe(faker.name().firstName(), faker.name().lastName(), faker.name().username(), "password", role);
        for (Competence c : competences) {
            employe.addCompetence(c);
        }
        if (projets != null){
            for (Projet p : projets) {
                employe.addProjet(p);
            }
        }
        employeRepository.save(employe);
        return employe;
    }

    private Projet populateProjet(String name, String description, Employe chef) {
        Projet projet = new Projet(name, description, chef);
        projetRepository.save(projet);
        return projet;
    }
}
