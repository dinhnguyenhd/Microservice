package dinhnguyen.techs.orders.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dinhnguyen.techs.orders.entitys.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}