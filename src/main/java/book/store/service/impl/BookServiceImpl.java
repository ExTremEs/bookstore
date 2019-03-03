package book.store.service.impl;

import static org.springframework.http.HttpMethod.GET;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import book.store.dto.BookDto;
import book.store.dto.BooksDto;
import book.store.service.BookService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@CacheConfig(cacheNames = "allBooks")
public class BookServiceImpl implements BookService {
	
	private final String BOOK_PUBLISHER_URL = "https://scb-test-book-publisher.herokuapp.com/books";
	private final String BOOK_PUBLISHER_RECOMMEND_URL = "https://scb-test-book-publisher.herokuapp.com/books/recommendation";
	
	@Override
	public BookDto getBook(Long bookId) {
		BooksDto booksDto = getAllBooks();
		for (BookDto bookDto : booksDto.getBooks()) {
			if (bookDto.getId().equals(bookId)) {
				return bookDto;
			}
		}
		return null;
	}
	
	@Override
	public BooksDto getAllBooks() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<Book>> booksResponse = restTemplate.exchange(BOOK_PUBLISHER_URL, GET, null, new ParameterizedTypeReference<List<Book>>(){});
		ResponseEntity<List<Book>> booksRecommendResponse = restTemplate.exchange(BOOK_PUBLISHER_RECOMMEND_URL, GET, null, new ParameterizedTypeReference<List<Book>>(){});
		
		List<Book> books = booksResponse.getBody();
		List<Book> booksRecommend = booksRecommendResponse.getBody();
		List<BookDto> bookDtos = makeBookDtoList(books, booksRecommend);
		
		return new BooksDto(bookDtos);
	}
	
	private List<BookDto> makeBookDtoList(List<Book> books, List<Book> booksRecommend) {
		
		List<BookDto> bookDtos = new ArrayList<BookDto>();
		
		for (Book book : books) {
			
			BookDto bookDto = new BookDto();
			bookDto.setAuthor(book.getAuthor_name());
			bookDto.setId(book.getId());
			bookDto.setName(book.getBook_name());
			bookDto.setPrice(book.getPrice());
			
			if (booksRecommend.contains(book)) {
				bookDto.setRecommended(true);
			}
			
			bookDtos.add(bookDto);
		}
		
		bookDtos = 
		bookDtos.stream()
				.sorted(Comparator.comparing(BookDto::getName))
				.sorted(Comparator.comparing(BookDto::isRecommended, Comparator.reverseOrder()))
				.collect(Collectors.toList());
		
		return bookDtos;
	}
}

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
class Book {
	private String author_name;
	private Long id;
	private String book_name;
	private BigDecimal price;
	private boolean is_recommend;
	
	
}
