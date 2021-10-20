package gestionnaire;

import gestionnaire.model.Employe;
import gestionnaire.repository.EmployeRepository;
import gestionnaire.repository.RoleRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeTest {

    @Autowired
    EmployeRepository employeRepository;

    @Autowired
    RoleRepository roleRepository;

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
        employeRepository.delete(employeRepository.findByNomAndPrenom("Arnaud","Jean").get(0));
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        employeRepository.save(employe);
        Employe employe2 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        employe2.setNom("Zidane");
        employeRepository.save(employe2);
        Employe result = employeRepository.findByNomAndPrenom("Zidane", "Jean").get(0);
        assertEquals("Zidane",result.getNom());
    }

    @Test
    @Order(3)
    public void testDeleteEmployee(){
        employeRepository.delete(employeRepository.findByNomAndPrenom("Arnaud","Jean").get(0));
        Employe employe = new Employe("Arnaud","Jean","jean","jean",roleRepository.findById(1L).get());
        System.out.println(employe);
        employeRepository.save(employe);
        Employe employe2 = employeRepository.findByNomAndPrenom("Arnaud", "Jean").get(0);
        employeRepository.delete(employe2);
        assertEquals(0, employeRepository.findByNomAndPrenom("Arnaud", "Jean").size());
    }
}
