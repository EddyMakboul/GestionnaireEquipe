package gestionnaire;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.*;
import gestionnaire.service.EmployeService;
import gestionnaire.service.ProjetService;
import gestionnaire.service.TacheService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TacheTest {
    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    EmployeService employeService;

    @Autowired
    ProjetService projetService;

    @Autowired
    TacheService tacheService;

    @Autowired
    TacheRepository tacheRepository;

    @Autowired
    ProjetRepository projetRepository;

    @BeforeEach
    public void setUp(){
        tacheService.deleteAllTask();
    }

    public Tache createTask(){
        Employe employe = employeRepository.findAll().get(1);
        Projet projet = projetRepository.findAll().get(1);
        Tache tache = new Tache("aaaa", "tache test", employe, projet);
        tache = tacheRepository.save(tache);
        return tache;
    }

    @Test
    @Order(1)
    public void testNewTask(){
        Tache tache = createTask();
        Long employeid = tache.getEmploye().getId();
        Long projetid = tache.getProjet().getId();

        Employe employe = employeService.findById(employeid);
        Projet projet =  projetService.findById(projetid);

        assertEquals("aaaa", employe.getTaches().get(0).getNom_tache());
        assertEquals("aaaa", projet.getTaches().get(0).getNom_tache());
        assertEquals("aaaa", tache.getNom_tache());
        assertEquals(tache.getEmploye().getId(), employeid);
        assertEquals(tache.getProjet().getId(), projetid);
    }

    @Test
    @Order(2)
    public void testUpdateTask(){
        Tache tache = createTask();

        Employe employe2 = employeRepository.findAll().get(2);
        Projet projet2 = projetRepository.findAll().get(2);
        tache.setEmploye(employe2);
        tache.setProjet(projet2);
        tache.setFinished(true);
        tache = tacheRepository.save(tache);

        Long employeid = employe2.getId();
        Long projetid = projet2.getId();

        employe2 = employeService.findById(employeid);
        projet2 = projetService.findById(projetid);

        assertEquals("aaaa", employe2.getTaches().get(0).getNom_tache());
        assertEquals("aaaa", projet2.getTaches().get(0).getNom_tache());
        assertEquals("aaaa", tache.getNom_tache());
        assertEquals(tache.getEmploye().getId(), employeid);
        assertEquals(tache.getProjet().getId(), projetid);
    }

    @Test
    @Order(3)
    public void testDeleteTask(){
        Tache tache = createTask();
        Long employeid = tache.getEmploye().getId();
        Long projetid = tache.getProjet().getId();

        tache.setEmploye(null);
        tache.setEmploye(null);
        tacheRepository.save(tache);
        tacheRepository.delete(tache);

        Employe employe = employeService.findById(employeid);
        Projet projet = projetService.findById(projetid);

        assertEquals(0, employe.getTaches().size());
        assertEquals(0, projet.getTaches().size());
        assertTrue(tacheRepository.findById(tache.getId()).isEmpty());
    }

}
