package user;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import user.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "passwordHash", ignore = true)
    UserDto toUserDto(User user);

    @Mapping(target = "passwordHash", source = "plainPassword")
    User toUser(UserDto userDto);


    default String mapPasswordHash(UserDto userDto) {
        if (userDto.getPlainPassword() != null && !userDto.getPlainPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(userDto.getPlainPassword());
        }
        return null;
    }
    default void updateUserFromDTO(UserDto userDto, @MappingTarget User user) {
        if (userDto.getPlainPassword() != null && !userDto.getPlainPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPasswordHash(encoder.encode(userDto.getPlainPassword()));
        }
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAdmin(userDto.isAdmin());
        user.setBirthDate(userDto.getBirthDate());
    }

    @AfterMapping
    default void afterUserDTOToUser(UserDto userDto, @MappingTarget User user) {
        if (userDto.getPlainPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPasswordHash(encoder.encode(userDto.getPlainPassword()));
        }
    }
}
