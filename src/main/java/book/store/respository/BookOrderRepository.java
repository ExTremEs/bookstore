package book.store.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import book.store.entity.BookOrder;

@Repository
public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {

}
