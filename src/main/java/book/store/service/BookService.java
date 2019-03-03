package book.store.service;

import book.store.dto.BookDto;
import book.store.dto.BooksDto;

public interface BookService {

	BooksDto getAllBooks();
	BookDto getBook(Long bookId);
}
