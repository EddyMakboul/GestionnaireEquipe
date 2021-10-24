package gestionnaire.service;

import com.github.javafaker.Faker;
import gestionnaire.model.*;
import gestionnaire.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Service permettant d'initialisé les valeurs pour chacun des éléments de l'application :
 *  projet, employe, role et tache
 */

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
        Role developpeurRole = new Role("Développeur");
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

        Projet projet1 = projetRepository.save(new Projet("EDT Hotel", "Application permettant la gestion de l'emploi du temps des employés d'un hotel.", chef1));
        Projet projet2 = projetRepository.save(new Projet("BuyNow", "Application de vente en ligne d'objet du quotidien.", chef2));
        Projet projet3 = projetRepository.save(new Projet("BeFresh", "Application de gestion de stock pour une entreprise agro-alimentaire.", chef3));
        Projet projet4 = projetRepository.save(new Projet("DUNE VR", "Application VR permettant de vivre le film DUNE dans la peau du personnage principal.", chef4));
        Projet projet5 = projetRepository.save(new Projet("SFLog21", "Création du nouveau logiciel d'optimisation moteur pour Ferrari.", chef5));

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

        tacheRepository.save(new Tache("Création des controllers", "Créer les controllers principaux permettant le fonctionnement basique de l'application", employe1, projet1));
        tacheRepository.save(new Tache("Ajout heure de travail", "Permettre l'ajout de nouvelles heures de travail dans l'emploi du temps d'un employé", employe2, projet1));
        tacheRepository.save(new Tache("Affichage graphique EDT", "Réaliser l'affichage graphique de l'application", employe3, projet1));
        tacheRepository.save(new Tache("Diagramme UML", "Réaliser tous les diagrammes UML de l'application", employe4, projet2));
        tacheRepository.save(new Tache("Api de vente", "Réaliser et utiliser l'API de vente sécurisé", employe5, projet2));
        tacheRepository.save(new Tache("Login", "Créer la page front et back de login pour les utilisateurs", employe6, projet2));
        tacheRepository.save(new Tache("MCD", "Réaliser le Modele de donnée intégral pour la gestion des stock", employe7, projet3));
        tacheRepository.save(new Tache("Supprimer des éléments", "Possibilité de supprimer des produits de la base de donnée", employe8, projet3));
        tacheRepository.save(new Tache("Front de l'accueil", "Réaliser le front de la page d'accueil de la gestion du stock", employe9, projet3));
        tacheRepository.save(new Tache("Modélisation 3D", "Réaliser la modélisation 3D de éléments présents", employe10, projet4));
        tacheRepository.save(new Tache("Documents spécification", "Réaliser les documents de spécification à envoyer au client pour la fin de semaine", employe2, projet4));
        tacheRepository.save(new Tache("Initialisation BD", "Initilaiser les éléments de la base de données", employe5, projet4));
        tacheRepository.save(new Tache("Affichage conducteur", "Réaliser un affichage permettant au conducteur de la voiture de changer la puissance moteur", employe1, projet5));
        tacheRepository.save(new Tache("Tester les composants", "Faire les test unitaires de performances des nouveaux composants", employe4, projet5));
        tacheRepository.save(new Tache("Modification de la langue", "Création d'un élément permettant la modification de la langue EN/IT", employe9, projet5));
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
