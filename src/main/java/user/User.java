package user;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCrypt;
import user.dto.Create;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(groups = {Create.class})
    @Column(name = "name")
    private String name;
    @Email
    @NotBlank(groups = {Create.class})
    @Column(name = "email")
    private String email;
    @Column(name = "is_admin")
    private boolean isAdmin;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "birthDate")
    private LocalDate birthDate;
    @NotBlank
    private String passwordHash;

    // Метод для проверки пароля
    public boolean checkPassword(char[] password) {
        String encodedPassword = new String(password);
        boolean isValid = BCrypt.checkpw(encodedPassword, passwordHash);
        Arrays.fill(password, ' ');
        return isValid;
    }
}
