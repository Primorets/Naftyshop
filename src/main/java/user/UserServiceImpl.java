package user;

import exception.DuplicateEmailException;
import exception.UserNotFoundException;
import exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pageable.Pagination;
import user.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public void registerUser(User user, char[] password) {
        String hashedPassword = encoder.encode(new String(password));
        user.setPasswordHash(hashedPassword);// Очищаем исходный массив
        Arrays.fill(password, ' ');
    }

    public boolean validatePassword(char[] password, String storedHash) {
        String temp = new String(password);
        boolean isValid = encoder.matches(temp, storedHash);
        Arrays.fill(password, ' ');// Очищаем исходный массив
        return isValid;
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toUserDto(userRepository.findById(id).orElseThrow(()
                -> new UserNotFoundException("Пользователь не был зарегестрирован.")));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserDto).collect(toList());
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow();
        userMapper.updateUserFromDTO(userDto, existingUser);
        userRepository.save(existingUser);
        return userMapper.toUserDto(existingUser);
    }

    @Transactional
    @Override
    public UserDto createUser(UserDto user) {
        validateUser(user);
        try {
            return userMapper.toUserDto(userRepository.save(userMapper.toUser(user)));
        } catch (DuplicateEmailException duplicateEmailException) {
            throw new DuplicateEmailException("Email уже зарегестрирован");
        }
    }

    @Override
    public List<UserDto> searchUserByName(String text, int from, int size) {
        return userRepository.searchUserByName(text, Pagination.makePageRequest(from, size)).stream()
                .filter(user -> user.getName().contains(text))
                .map(userMapper::toUserDto)
                .collect(toList());
    }


    public boolean checkId(Long userId, Long friendId) {
        if (userId < 0 || friendId < 0) {
            throw new UserNotFoundException("ID не существует.");
        }
        getUserById(userId);
        getUserById(friendId);
        return true;
    }

    private void validateUser(UserDto user) {
        if (!user.getEmail().contains("@") || user.getEmail() == null) {
            throw new ValidationException("Введён не правильный email");
        }
        if (user.getName().isEmpty() || user.getName().contains(" ")) {
            throw new ValidationException("Введено пустое имя");
        }
    }

}
