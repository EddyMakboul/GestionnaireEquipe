package gestionnaire;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.ProjetRepository;
import gestionnaire.repository.TaskRepository;
import gestionnaire.service.EmployeService;
import gestionnaire.service.ProjetService;
import gestionnaire.service.TacheService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

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
    TaskRepository taskRepository;

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


        Tache tache = taskRepository.findAll().get(1);
        tache.setEmploye(employe1);
        tache.setProjet(projet);
        taskRepository.save(tache);

        return projetService.findById(projet.getId());
    }

    @Test
    @Order(1)
    public void testNewProjet(){
        Projet projet = createProjet();

        Employe chef = employeRepository.findAll().get(1);
        assertEquals(chef.getId(), projet.getChef_projet().getId());

        Tache tache = taskRepository.findAll().get(1);
        assertEquals(tache.getId(),projet.getTaches().get(0).getId());
        assertEquals(tache.getProjet().getId(),projet.getId());

        Employe employe1 = employeService.findById(employeRepository.findAll().get(2).getId());

        assertEquals(employe1.getId(), projet.getEmployes().get(0).getId());
        assertEquals(employe1.getProjets().get(0).getId(), projet.getId());
    }

    @Test
    @Order(2)
    public void testUpdateProjet(){
        
    }

    @Test
    @Order(3)
    public void testDeleteProjet(){
        Projet projet = createProjet();

        Tache tache = taskRepository.findAll().get(1);
        projetService.deleteProjetById(projet.getId());

        Employe employe1 = employeService.findById(employeRepository.findAll().get(2).getId());

        assertTrue(projetRepository.findById(projet.getId()).isEmpty());
        assertEquals(0,employe1.getProjets().size());
        assertTrue(taskRepository.findById(tache.getId()).isEmpty());

    }
}
