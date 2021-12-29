package otus.learning.vsdxv.homework1.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private long userId;
    private String username;
    private String firstName;
    private String lastName;
    private int age;
    private int gender;
    private String interests;
    private String city;
    @Length(min = 5, message = "*Пароль должен быть от 5 символов")
    @NotEmpty(message = "*Введите пароль")
    private String password;
    private boolean enabled;
}
