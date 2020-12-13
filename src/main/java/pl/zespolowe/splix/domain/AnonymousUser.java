package pl.zespolowe.splix.domain;

import lombok.Getter;

@Getter
public class AnonymousUser extends AbstractUser {

    public AnonymousUser(String username) {
        super(username, "");
        addRole(Role.GUEST);
    }
}
