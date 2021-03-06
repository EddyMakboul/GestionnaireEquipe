package gestionnaire.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import gestionnaire.model.Role;
import gestionnaire.repository.RoleRepository;


/**
 * Controller pour les roles, permet de gérer actions pour les roles
 */
@Controller
@RequestMapping("/api/roles")
public class RoleController {

	
	@Autowired
	RoleRepository roleRepo;


	/**
	 * Methode GET, permet de récupérer tous les roles
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Role>> getAllRole(){
		return new ResponseEntity<>(roleRepo.findAll(),HttpStatus.OK);
	}

	/**
	 * Methode GET, permet de récupérer un role via son id
	 * @param id d'un role
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable long id){
		return new ResponseEntity<>(roleRepo.findById(id).get(),HttpStatus.OK);
	}
}
