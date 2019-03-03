package book.store.service;

import book.store.dto.UserDto;

public interface UserService {

	void createUser(UserDto userDto);
	UserDto getUser();
	void deleteUser();
}
