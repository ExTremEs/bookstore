package book.store.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import book.store.dto.OrderDto;
import book.store.dto.UserDto;
import book.store.exception.BookstoreException;
import book.store.service.OrderService;
import book.store.service.UserService;

@RestController//("/users")
public class UserController {
	
	private Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderService orderService;

	@PostMapping("/users")
	public void createUser(@RequestBody UserDto userDto) throws BookstoreException {
		try {
			userService.createUser(userDto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BookstoreException(e); 
		}
	}
	
	@GetMapping("/users")
	public UserDto getUser() throws BookstoreException {
		try {
			return userService.getUser();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BookstoreException(e); 
		}
	}
	
	@DeleteMapping("/users")
	public void deleteUser() throws BookstoreException {
		try {
			userService.deleteUser();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BookstoreException(e); 
		}
	}
	
	@PostMapping("/users/orders")
	public OrderDto createOrder(@RequestBody OrderDto orderDto) throws BookstoreException {
		try {
			return orderService.createOrder(orderDto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BookstoreException(e); 
		}
	}
}
