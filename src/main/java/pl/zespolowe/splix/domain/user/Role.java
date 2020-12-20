package pl.zespolowe.splix.domain.user;

/**
 * User role in application
 *
 * @author Tomasz
 */
public enum Role {
    USER,
    ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name().toUpperCase();
    }
}
