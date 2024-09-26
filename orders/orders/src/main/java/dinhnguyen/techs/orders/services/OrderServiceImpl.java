package dinhnguyen.techs.orders.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import dinhnguyen.techs.commons.exceptions.RecordNotFoundExceptions;
import dinhnguyen.techs.commons.kafka.KafkaLogsSender;
import dinhnguyen.techs.orders.entitys.Order;
import dinhnguyen.techs.orders.entitys.Product;
import dinhnguyen.techs.orders.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private Gson gson;

	@Autowired
	private KafkaLogsSender kafkaLogsSender;

	@Override
	public List<Order> list() {
		return (List<Order>) this.orderRepository.findAll();
	}

	@Override
	public Order save(Order object) {
		Order order = new Order();
		try {
			List<Product> list = object.getProducts();
			for (Product product : list) {
				product.setOrder(object);
			}
			order = this.orderRepository.save(object);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public Order findById(Long id) {

		Optional<Order> order = this.orderRepository.findById(id);
		if (order.isPresent()) {
			return order.get();
		} else {
			String json = this.kafkaLogsSender.convert("order-service", "OrderServiceImpl", id.toString(),"Record_Not_Found_Exception");
			kafkaLogsSender.send("logs", json);
			throw new RecordNotFoundExceptions("Record_Not_Found_Exception");
		}
	}

	@Override
	public Order update(Order object) {
		Order output = new Order();
		Optional<Order> order = this.orderRepository.findById(object.getId());
		if (order.isPresent()) {
			output = this.orderRepository.save(object);
			return output;
		} else {
			String json = this.kafkaLogsSender.convert("order-service", "OrderServiceImpl", this.gson.toJson(object),"Record_Not_Found_Exception");
			kafkaLogsSender.send("logs", json);
			throw new RecordNotFoundExceptions("Record_Not_Found_Exception");
		}
	}

	@Override
	public void delete(Long id) {
		Optional<Order> order = this.orderRepository.findById(id);
		if (order.isPresent()) {
			this.orderRepository.deleteById(id);
		} else {
			String json = this.kafkaLogsSender.convert("order-service", "OrderServiceImpl", id.toString(),"Record_Not_Found_Exception");
			kafkaLogsSender.send("logs", json);
			throw new RecordNotFoundExceptions("Record_Not_Found_Exception");
		}

	}

}
