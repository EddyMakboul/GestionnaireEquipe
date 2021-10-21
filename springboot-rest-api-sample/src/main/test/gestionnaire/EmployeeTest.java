package gestionnaire;

import gestionnaire.model.Employe;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.RoleRepository;
import gestionnaire.service.EmployeeService;
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
    EmployeeService employeeService;

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void setUp(){
        employeeService.deleteAllEmployee();
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

    
}
