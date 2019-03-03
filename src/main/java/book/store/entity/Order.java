package book.store.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "ORDERS")
public class Order {

	@Id
    @GeneratedValue(strategy = AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "order", fetch = EAGER, cascade = ALL)
	private Set<BookOrder> bookOrders;
}
