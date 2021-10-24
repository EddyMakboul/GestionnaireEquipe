package gestionnaire;

import gestionnaire.model.Competence;
import gestionnaire.model.Employe;
import gestionnaire.model.Projet;
import gestionnaire.model.Tache;
import gestionnaire.repository.*;
import gestionnaire.service.EmployeService;
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
    EmployeService employeService;

    @Autowired
    CompetenceRepository competenceRepository;

    @Autowired
    TacheRepository tacheRepository;

    @Autowired
    ProjetRepository projetRepository;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void setUp(){
        employeService.deleteAllEmployee();
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
        Employe employe2 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        assertEquals(0,employe2.getCompetences().size());
        Competence competence = competenceRepository.findById(1L).get();
        employe2.addCompetence(competence);
        employeRepository.save(employe2);
        Employe employe3 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
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
        Employe employe2 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Competence> competences = employe2.getCompetences();
        assertEquals(competences.get(0).getId(), competence.getId());
        employe2.deleteCompetence(competence);
        employeRepository.save(employe2);
        Employe employe3 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        competences = employe3.getCompetences();
        assertEquals(0,competences.size());
    }

    @Test
    @Order(6)
    public void testAddEmployeeTache(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        assertEquals(0,employe2.getTaches().size());
        Tache tache = tacheRepository.findAll().get(0);
        tache.setEmploye(employe2);
        tacheRepository.save(tache);
        Employe employe3 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Tache> taches = employe3.getTaches();
        assertEquals(taches.get(0).getId(), tache.getId());
    }

    @Test
    @Order(7)
    public void testRemoveEmployeeTache(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        assertEquals(0,employe2.getTaches().size());
        Tache tache = tacheRepository.findAll().get(0);
        tache.setEmploye(employe2);
        tacheRepository.save(tache);
        Employe employe3 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Tache> taches = employe3.getTaches();
        assertEquals(taches.get(0).getId(), tache.getId());
        tache.setEmploye(null);
        tacheRepository.save(tache);
        Employe employe4 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        taches = employe4.getTaches();
        assertEquals(0, taches.size());
    }

    @Test
    @Order(8)
    public void testAddEmployeeProjet(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        assertEquals(0,employe2.getProjets().size());
        Projet projet = projetRepository.findAll().get(0);
        employe2.addProjet(projet);
        employeRepository.save(employe2);
        Employe employe3 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Projet> projets = employe3.getProjets();
        assertEquals(projets.get(0).getId(), projet.getId());
    }

    @Test
    @Order(9)
    public void testRemoveEmployeeProjet(){
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        assertEquals(0,employe2.getProjets().size());
        Projet projet = projetRepository.findAll().get(0);
        employe2.addProjet(projet);
        employeRepository.save(employe2);
        Employe employe3 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        List<Projet> projets = employe3.getProjets();
        assertEquals(projets.get(0).getId(), projet.getId());
        employe3.deleteProjet(projet);
        employeRepository.save(employe3);
        Employe employe4 = employeService.findByNomAndPrenom("Arnaud", "Jean").get(0);
        projets = employe4.getProjets();
        assertEquals(0, projets.size());
    }
}
