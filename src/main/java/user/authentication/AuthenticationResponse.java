package user.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import user.User;
import user.dto.UserDto;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AuthenticationResponse {
    private String token;
    private UserDto user;

}
