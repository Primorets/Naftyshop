package user.authentication;

import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import user.User;
import user.UserMapper;
import user.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = validateCredentials(request);
        String jwtToken = JwtUtils.generateToken(user);
        return new AuthenticationResponse(jwtToken, userMapper.toUserDto(user));
    }

    private User validateCredentials(AuthenticationRequest request) {
        try {
            User user = userRepository.findUserByEmail(request.getEmail());
            if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                throw new BadCredentialsException("Invalid email or password");
            }
            return user;
        } catch ( UserNotFoundException userNotFoundException){
            throw new UserNotFoundException("Invalid email");
        }
    }
}
