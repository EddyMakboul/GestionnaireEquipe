package gestionnaire.repository;

import gestionnaire.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository permettant les operations sur les rôles
 */

public interface RoleRepository extends JpaRepository<Role, Long> {
}
