package book.store.exception;

import java.io.Serializable;

public class BookstoreException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public BookstoreException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
	
	public BookstoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
