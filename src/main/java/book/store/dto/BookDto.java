package book.store.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BookDto {

	private Long id;
	private String name;
	private String author;
	private BigDecimal price;
	private boolean isRecommended;
}
