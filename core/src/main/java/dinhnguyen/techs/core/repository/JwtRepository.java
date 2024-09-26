package dinhnguyen.techs.core.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dinhnguyen.techs.core.entity.Jwtoken;

@Repository
public interface JwtRepository extends JpaRepository<Jwtoken, Long> {

	// Condition: Token chua bi thu hoi:
	@Query(value = "SELECT * FROM Jwt jwt WHERE jwt.email = :email and revoked = 0", nativeQuery = true)
	public Jwtoken getTokenByEmail(@Param("email") String email);
	
	// Condition: Token chua bi thu hoi:
	//@Query(value = "SELECT * FROM Jwt jwt WHERE jwt.refresh_token = :refreshToken and revoked = 1", nativeQuery = true)
		@Query(value = "SELECT * FROM Jwt jwt WHERE jwt.refresh_token = :refreshToken limit 1", nativeQuery = true)
		public Optional<Jwtoken> getTokenByRefreshToken(@Param("refreshToken") String refreshToken);

}
