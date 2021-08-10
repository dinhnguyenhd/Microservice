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
import dinhnguyen.techs.mongodb.entitys.Product;
import dinhnguyen.techs.mongodb.forms.SearchProductForm;
import dinhnguyen.techs.mongodb.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private KafkaLogsSender kafkaSender;

	@Override
	public List<Product> list() {
		return this.productRepository.findAll();
	}

	@Override
	public Product save(Product object) {
		return this.productRepository.save(object);
	}

	@Override
	public Product update(Product object) {
		return this.productRepository.save(object);
	}

	@Override
	public Product findByIds(String id) {
		Product product = new Product();
		Optional<Product> opt = this.productRepository.findById(id);
		if (opt.isPresent()) {
			product = opt.get();
		} else {
			String json = this.kafkaSender.convert("product-service", "UserServiceImpl", id,
					"The record not found exception");
			this.kafkaSender.send("logs", json);
			throw new RecordNotFoundExceptions("The record not found exception");
		}
		return product;
	}

	@Override
	public void delete(Product object) {
		this.productRepository.delete(object);
	}

	@Override
	public void deleteById(String id) {
		this.productRepository.deleteById(id);
	}

	@Override
	public Product findbyName(String name) {
		Product product = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));
		product = mongoTemplate.find(query, Product.class).get(0);
		if (product == null) {
			String json = this.kafkaSender.convert("product-service", "UserServiceImpl", name,
					"Product with " + name + " Not Found in DB");
			this.kafkaSender.send("logs", json);
			throw new RecordNotFoundExceptions("Product with " + name + " Not Found in DB");
		}
		return product;
	}

	@Override
	public List<Product> complexSearch(SearchProductForm form) {
		Query query = new Query();

		if (form.getName() != null && form.getName().trim().length() != 0) {
			query.addCriteria(Criteria.where("name").is(form.getName().trim()));
		}

		if (form.getMinPrice() > 0 && (form.getMaxPrice() > form.getMinPrice())) {
			query.addCriteria(Criteria.where("price").gte(form.getMinPrice()).lt(form.getMinPrice()));
		}
		List<Product> list = mongoTemplate.find(query, Product.class);
		return list;
	}

	@Override
	public Product saveComplex(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

}
