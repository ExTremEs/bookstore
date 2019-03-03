package book.store.service;

import book.store.dto.OrderDto;

public interface OrderService {

	OrderDto createOrder(OrderDto orderDto);
}
