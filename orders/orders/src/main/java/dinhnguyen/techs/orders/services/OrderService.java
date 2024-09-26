package dinhnguyen.techs.orders.services;

import java.util.List;

import dinhnguyen.techs.orders.entitys.Order;

public interface OrderService {

	public List<Order> list();

	public Order save(Order object);

	public Order findById(Long id);

	public Order update(Order object);

	public void delete(Long id);
}
