package cphbusiness.noinputs.main.repository;

import cphbusiness.noinputs.main.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
