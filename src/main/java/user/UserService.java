package user;

import user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);

    List<UserDto> getAllUsers();

    void deleteUserById(Long id);

    UserDto updateUser(UserDto user, Long id);

    UserDto createUser(UserDto user);

    List<UserDto> searchUserByName(String text, int from, int size);
}
