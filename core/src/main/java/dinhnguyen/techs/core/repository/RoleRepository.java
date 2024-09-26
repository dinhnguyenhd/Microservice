package dinhnguyen.techs.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dinhnguyen.techs.core.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
