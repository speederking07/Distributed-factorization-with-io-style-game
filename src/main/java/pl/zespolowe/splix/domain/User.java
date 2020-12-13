package pl.zespolowe.splix.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class User extends AbstractUser {


    @Email(message = "email%Invalid email address")
    @NotBlank(message = "email%Email cannot be blank")
    private String email;


    public User() {
        super();
        addRole(Role.USER);
        addRole(Role.GUEST);
    }

    public User(String username, String password, String email) {
        super(username, password);
        addRole(Role.USER);
        addRole(Role.GUEST);
        this.email = email;
    }

    public void setAdmin(){
        addRole(Role.ADMIN);
    }
}
