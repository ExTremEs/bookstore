package book.store.entity;

import static javax.persistence.GenerationType.AUTO;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "BOOK_ORDER")
public class BookOrder {

	@Id
    @GeneratedValue(strategy = AUTO)
	private Long id;
	
	@Column(name = "BOOK_ID")
	private Long bookId;
	
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;
}
