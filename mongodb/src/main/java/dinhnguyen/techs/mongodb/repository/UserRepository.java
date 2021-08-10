package dinhnguyen.techs.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import dinhnguyen.techs.mongodb.entitys.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	@Query(" { email : ?0 } ")
	public User findbyEmail(String email);

}
