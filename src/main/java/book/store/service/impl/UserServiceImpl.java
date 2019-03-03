package book.store.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import book.store.dto.UserDto;
import book.store.entity.BookOrder;
import book.store.entity.Order;
import book.store.entity.User;
import book.store.respository.UserRepository;
import book.store.service.UserService;
import book.store.util.SecurityUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

	@Autowired
	public PasswordEncoder encoder;
	
	@Autowired
	private UserRepository userRepository;
	
	private final String ROLE = "USER";
	
	@Override
	public void createUser(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setName(userDto.getName());
		user.setPassword(encoder.encode(userDto.getPassword()));
		user.setRole(ROLE);
		user.setSurname(userDto.getSurname());
		user.setDateOfBirth(userDto.getDateOfBirth());
        userRepository.save(user);
	}
	
	@Override
	public UserDto getUser() {
		
		String username = SecurityUtils.getUsername();
		
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException(username); 
		}
		
		User user = userRepository.findByUsername(username);
		UserDto userDto = makeUserDto(user);
		
		return userDto;
	}
	
	private UserDto makeUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setDateOfBirth(user.getDateOfBirth());
		userDto.setName(user.getName());
		userDto.setSurname(user.getSurname());
		
		Set<Long> bookIds = new HashSet<Long>();
		
		if (user.getOrders() != null) {
			for (Order order : user.getOrders()) {
				if (order.getBookOrders() != null) {
					for (BookOrder bookOrder : order.getBookOrders()) {
						bookIds.add(bookOrder.getBookId());
					}
				}
			}
		}
		
		userDto.setBooks(bookIds);
		return userDto;
	}
	
	@Override
	public void deleteUser() {
		
		String username = SecurityUtils.getUsername();
		
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException(username); 
		}
		
		User user = userRepository.findByUsername(username);
		userRepository.delete(user);
	}
}
