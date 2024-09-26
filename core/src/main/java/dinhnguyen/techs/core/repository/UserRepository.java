package dinhnguyen.techs.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dinhnguyen.techs.core.entity.User;
import dinhnguyen.techs.core.projection.PJUser;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM User user WHERE user.email = :email", nativeQuery = true)
	Optional<User> getUserByEmail(@Param("email") String email);

	Boolean existsByEmail(String email);

	@Query("SELECT user FROM User user WHERE user.name LIKE %:searchWhat% ")
	Page<User> search(@Param("searchWhat") String searchWhat, Pageable pageable);

	@Query("SELECT user FROM User user WHERE user.name LIKE %:searchName% ")
	List<User> excelExport(@Param("searchName") String searchName);

	@Query(value = "select user.user_id as id, user.name as name, user.email as email from User user where user.approve = 0", nativeQuery = true)
	public Page<PJUser> listUser(Pageable pageable);

}
