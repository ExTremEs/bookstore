package book.store.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import book.store.dto.BooksDto;
import book.store.exception.BookstoreException;
import book.store.service.BookService;

@RestController("/books")
public class BookController {
	
	private Logger log = Logger.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public BooksDto getAllBooks() throws BookstoreException {
		try {
			return bookService.getAllBooks();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BookstoreException(e); 
		}
	}
}
