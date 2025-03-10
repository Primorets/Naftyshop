package user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(groups = {Create.class})
    private String name;

    @Email
    @NotBlank(groups = {Create.class})
    private String email;

    private boolean isAdmin;

    @JsonIgnore
    private String password; // для передачи открытого пароля при регистрации

    private LocalDateTime createdAt;

    private LocalDate birthDate;

    // Временное поле для передачи пароля при создании
    @JsonIgnore
    @Transient
    private String plainPassword;

    // Метод для получения хешированного пароля
    public String getPasswordHash() {
        if (plainPassword != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.encode(plainPassword);
        }
        return null;
    }
}
