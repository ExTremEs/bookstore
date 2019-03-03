package book.store.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import book.store.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
