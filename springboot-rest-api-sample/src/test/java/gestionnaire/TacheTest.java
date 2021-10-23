package gestionnaire;

import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TacheTest {
    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjetRepository projetRepository;

    @BeforeEach
    public void setUp(){
        List<Tache> taches = taskRepository.findAll();
        for (Tache tache: taches){
            tache.setEmploye(null);
            tache.setProjet(null);
            taskRepository.save(tache);
        }
        taskRepository.deleteAll();
    }

    @Test
    @Order(1)
    @Transactional
    public void testNewTask(){
        Employe employe = employeRepository.findAll().get(1);
        Projet projet = projetRepository.findAll().get(1);
        Long projetid = projet.getId();
        Long employeid = employe.getId();
        Tache tache = new Tache("aaaa", "tache test", employe, projet);
        tache = taskRepository.save(tache);
        Long tacheid = tache.getId();
        tache = taskRepository.findById(tacheid).get();
        employe = employeRepository.findById(employeid).get();
        employe.getTaches().size();
        projet = projetRepository.findById(projetid).get();
        projet.getTaches().size();
        assertEquals("aaaa", employe.getTaches().get(0).getNom_tache());
        assertEquals("aaaa", projet.getTaches().get(0).getNom_tache());
        assertEquals("aaaa", tache.getNom_tache());


    }

    @Test
    @Order(2)
    public void testUpdateTache(){

    }

    @Test
    @Order(3)
    public void testDeleteTache(){

    }

}
