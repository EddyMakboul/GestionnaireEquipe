package gestionnaire;

import gestionnaire.model.Competence;
import gestionnaire.model.Employe;
import gestionnaire.model.Tache;
import gestionnaire.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeTest {

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    CompetenceRepository competenceRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ProjetRepository projetRepository;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void setUp(){
        System.out.println(taskRepository.findAll());
        employeRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testAddEmployee(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe result = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        assertEquals(employe.getLogin(),result.getLogin());
    }

    @Test
    @Order(2)
    public void testUpdateEmployee(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        employe2.setNom("Zidane");
        employeRepository.save(employe2);
        List<Employe> result = employeRepository.findByNomAndPrenom("Zidane", "Jean");
        assertEquals(1,result.size());
    }

    @Test
    @Order(3)
    public void testDeleteEmployee(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        employeRepository.delete(employe2);
        assertTrue(employeRepository.findById(1000L).isEmpty());
    }

    @Test
    @Order(4)
    public void testAddEmployeeCompetence(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        assertEquals(0,employe2.getCompetences().size());
        Competence competence = competenceRepository.findById(1L).get();
        employe2.addCompetence(competence);
        employeRepository.save(employe2);
        Employe employe3 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Competence> competences = employe3.getCompetences();
        assertEquals(competences.get(0).getId(), competence.getId());
    }

    @Test
    @Order(5)
    public void testRemoveEmployeeCompetence(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        Competence competence = competenceRepository.findById(1L).get();
        employe.addCompetence(competence);
        employeRepository.save(employe);
        Employe employe2 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Competence> competences = employe2.getCompetences();
        assertEquals(competences.get(0).getId(), competence.getId());
        employe2.deleteCompetence(competence);
        employeRepository.save(employe2);
        Employe employe3 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        competences = employe3.getCompetences();
        assertEquals(0,competences.size());
    }

    @Test
    @Order(6)
    public void testAddEmployeeTache(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        assertEquals(0,employe2.getTaches().size());
        Tache tache = taskRepository.findAll().get(0);
        employe2.addTache(tache);
        employeRepository.save(employe2);
        Employe employe3 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Tache> taches = employe3.getTaches();
        assertEquals(taches.get(0).getId(), tache.getId());
    }

    @Test
    @Order(7)
    public void testRemoveEmployeeTache(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        Tache tache = taskRepository.findAll().get(0);
        employe.addTache(tache);
        employeRepository.save(employe);
        Employe employe2 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Tache> taches = employe2.getTaches();
        assertEquals(taches.get(0).getId(), tache.getId());
        employe2.deleteTache(tache);
        employeRepository.save(employe2);
        Employe employe3 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        taches = employe3.getTaches();
        assertEquals(0,taches.size());
    }
}
