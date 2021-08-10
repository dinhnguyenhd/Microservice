package dinhnguyen.techs.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import dinhnguyen.techs.mongodb.entitys.Product;
import dinhnguyen.techs.mongodb.entitys.User;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	@Query(" { name : ?0 } ")
	public User findbyName(String email);

}
