package dinhnguyen.techs.mongodb.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import dinhnguyen.techs.commons.exceptions.RecordNotFoundExceptions;
import dinhnguyen.techs.commons.kafka.KafkaLogsSender;
import dinhnguyen.techs.mongodb.entitys.User;
import dinhnguyen.techs.mongodb.forms.SearchUserForms;
import dinhnguyen.techs.mongodb.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private KafkaLogsSender kafkaSender;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<User> list() {
		return this.userRepository.findAll();
	}

	@Override
	public User save(User object) {
		return this.userRepository.save(object);
	}

	@Override
	public User update(User object) {
		User user = null;
		try {
			Optional<User> opt = this.userRepository.findById(object.getId());
			if (opt.isPresent()) {
				user = this.userRepository.save(object);
			}
		} catch (Exception e) {
			throw new RecordNotFoundExceptions("Record with ID not found ");
		}
		return user;

	}

	@Override
	public User findByIds(String id) {
		User user = new User();
		System.out.println(" id " + id);
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		user = mongoTemplate.find(query, User.class).get(0);
		if (user == null) {
			String jsonLogs = this.kafkaSender.convert("product-service", "UserServiceImpl", id,
					"Record wtih ID " + id + " Not found");
			kafkaSender.send("logs", jsonLogs);
			throw new RecordNotFoundExceptions("Record wtih ID " + id + " Not found ");
		}
		return user;
	}

	@Override
	public void delete(User object) {
		this.userRepository.deleteById(object.getId());

	}

	@Override
	public void deleteById(String id) {
		this.userRepository.deleteById(id);
	}

	@Override
	public User findbyEmail(String email) {
		User user;
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("email").is(email));
			user = mongoTemplate.find(query, User.class).get(0);

		} catch (Exception e) {
			throw new RecordNotFoundExceptions("User with " + email + " Not Found in DB");
		}
		return user;
	}

	@Override
	public List<User> complexSearch(SearchUserForms form) {
		Query query = new Query();

		if (form.getName() != null && form.getName().trim().length() != 0) {
			query.addCriteria(Criteria.where("name").is(form.getName().trim()));
		}
		if ((form.getCity() != null) && (form.getCity().trim().length() != 0)) {
			query.addCriteria(Criteria.where("city").is(form.getCity()));
		}
		if ((form.getCountry() != null) && (form.getCountry().trim().length() != 0)) {
			query.addCriteria(Criteria.where("country").is(form.getCountry()));
		}
		if (form.getMinAge() > 0 && (form.getMaxAge() > form.getMinAge())) {
			query.addCriteria(Criteria.where("age").gte(form.getMinAge()).lt(form.getMaxAge()));
		}
		List<User> list = mongoTemplate.find(query, User.class);
		return list;
	}

}
