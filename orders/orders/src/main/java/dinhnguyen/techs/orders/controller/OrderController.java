package dinhnguyen.techs.orders.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dinhnguyen.techs.orders.entitys.Order;
import dinhnguyen.techs.orders.forms.OrderForm;
import dinhnguyen.techs.orders.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController implements CommonAction<OrderForm, Order> {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/lists")
	public ResponseEntity<List<Order>> lists() {
		List<Order> list = this.orderService.list();
		return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
	}
	
	@Override
	@GetMapping("/list")
	public ResponseEntity<List<Order>> list() {
		List<Order> list = this.orderService.list();
		return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
	}

	@Override
	@GetMapping("find/{id}")
	public ResponseEntity<Order> findById(Long id) {
		Order order = this.orderService.findById(Long.valueOf(id));
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@Override
	@PostMapping("/save")
	public ResponseEntity<Order> save(@Valid OrderForm object) {
		Order order = this.modelMapper.map(object, Order.class);
		order = this.orderService.save(order);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@Override
	@PostMapping("/update")
	public ResponseEntity<Order> update(@Valid OrderForm object) {
		Order order = this.modelMapper.map(object, Order.class);
		order = this.orderService.save(order);
		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

	@Override
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(Long id) {
		this.orderService.delete(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
