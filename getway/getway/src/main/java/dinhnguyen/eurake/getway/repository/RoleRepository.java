package dinhnguyen.eurake.getway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dinhnguyen.eurake.getway.entitys.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("from Role role where role.name = ?1")
	Optional<Role> roleAlreadyExit(String roleName);
}
