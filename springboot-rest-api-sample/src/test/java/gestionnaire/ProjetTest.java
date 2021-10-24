package gestionnaire;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.TacheRepository;
import gestionnaire.service.EmployeService;
import gestionnaire.service.ProjetService;
import gestionnaire.service.TacheService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjetTest {

    @Autowired
    EmployeService employeService;

    @Autowired
    ProjetService projetService;

    @Autowired
    TacheService tacheService;

    @Autowired
    TacheRepository tacheRepository;

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    ProjetRepository projetRepository;

    @BeforeEach
    public void setUp(){
        projetService.deleteAllProjet();
    }


    public Projet createProjet(){
        Employe chef = employeRepository.findAll().get(1);
        Projet projet = new Projet("aaaa", "projet 1", chef);
        projet = projetRepository.save(projet);

        Employe employe1 = employeRepository.findAll().get(2);
        employe1 = employeService.findById(employe1.getId());
        employe1.addProjet(projet);
        employeRepository.save(employe1);


        Tache tache = tacheRepository.findAll().get(1);
        tache.setEmploye(employe1);
        tache.setProjet(projet);
        tacheRepository.save(tache);

        return projetService.findById(projet.getId());
    }

    @Test
    @Order(1)
    public void testNewProjet(){
        Projet projet = createProjet();

        Employe chef = employeRepository.findAll().get(1);
        assertEquals(chef.getId(), projet.getChef_projet().getId());

        Tache tache = tacheRepository.findAll().get(1);
        assertEquals(tache.getId(),projet.getTaches().get(0).getId());
        assertEquals(tache.getProjet().getId(),projet.getId());

        Employe employe1 = employeService.findById(employeRepository.findAll().get(2).getId());

        assertEquals(employe1.getId(), projet.getEmployes().get(0).getId());
        assertEquals(employe1.getProjets().get(0).getId(), projet.getId());
    }

    @Test
    @Order(2)
    public void testUpdateProjet(){
        Projet projet = createProjet();

        Employe chef = employeRepository.findAll().get(3);

        Employe employe1 = employeRepository.findAll().get(2);
        employe1 = employeService.findById(employe1.getId());
        employe1.deleteProjet(projet);
        employeRepository.save(employe1);

        Employe employe2 = employeRepository.findAll().get(4);
        employe2 = employeService.findById(employe2.getId());
        employe2.addProjet(projet);
        employeRepository.save(employe2);

        Tache tache = tacheRepository.findAll().get(1);
        tache.setEmploye(null);
        tache.setProjet(null);
        tacheRepository.save(tache);

        tache = tacheRepository.findAll().get(1);
        tache.setEmploye(employe2);
        tache.setProjet(projet);
        tacheRepository.save(tache);

        projet.setChef_projet(chef);
        projetRepository.save(projet);

        projet = projetService.findById(projet.getId());

        assertEquals(employe2.getId(),projet.getEmployes().get(0).getId());
        assertEquals(tache.getId(),projet.getTaches().get(0).getId());
        assertEquals(chef.getId(),projet.getChef_projet().getId());

    }

    @Test
    @Order(3)
    public void testDeleteProjet(){
        Projet projet = createProjet();

        Tache tache = tacheRepository.findAll().get(1);
        projetService.deleteProjetById(projet.getId());

        Employe employe1 = employeService.findById(employeRepository.findAll().get(2).getId());

        assertTrue(projetRepository.findById(projet.getId()).isEmpty());
        assertEquals(0,employe1.getProjets().size());
        assertTrue(tacheRepository.findById(tache.getId()).isEmpty());

    }
}
