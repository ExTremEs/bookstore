package book.store.service.impl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import book.store.dto.BookDto;
import book.store.dto.OrderDto;
import book.store.entity.BookOrder;
import book.store.entity.Order;
import book.store.entity.User;
import book.store.respository.BookOrderRepository;
import book.store.respository.OrderRepository;
import book.store.respository.UserRepository;
import book.store.service.BookService;
import book.store.service.OrderService;
import book.store.util.SecurityUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BookOrderRepository bookOrderRepository;
	
	@Autowired
	private BookService bookService;
	
	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		
		String username = SecurityUtils.getUsername();
		
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException(username); 
		}
		
		User user = userRepository.findByUsername(username);
		
		Order order = new Order();
		order.setUser(user);
		order = orderRepository.save(order);
		
		Set<BookOrder> bookOrders = new HashSet<BookOrder>();
		BigDecimal price = new BigDecimal(0);
		
		for (Long bookId : orderDto.getOrders()) {
			BookDto booksDto = bookService.getBook(bookId);
			BookOrder bookOrder = new BookOrder();
			bookOrder.setBookId(booksDto.getId());
			bookOrder.setPrice(booksDto.getPrice());
			bookOrder.setOrder(order);
			bookOrder = bookOrderRepository.save(bookOrder);
			bookOrders.add(bookOrder);
			price = price.add(booksDto.getPrice());
		}
		
		OrderDto orderReturn = new OrderDto();
		orderReturn.setPrice(price);
		return orderReturn;
	}
}
