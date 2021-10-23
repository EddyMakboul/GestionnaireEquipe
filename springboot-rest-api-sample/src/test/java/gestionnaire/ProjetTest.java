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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

//    public Projet createProjet(){
//
//    }

    @Test
    @Order(1)
    public void testNewProjet(){

    }

    @Test
    @Order(2)
    public void testUpdateProjet(){

    }

    @Test
    @Order(3)
    public void testDeleteProjet(){

    }
}
