package book.store.entity;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "USERS")
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"name", "surname"})
})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = AUTO)
	private Long id;
	
	@Column(unique = true)
	private String username;
	private String password;
	private String name;
	private String surname;
	private String role;
	
	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;
	
	@OneToMany(mappedBy = "user", cascade = REMOVE)
	private Set<Order> orders;
}
