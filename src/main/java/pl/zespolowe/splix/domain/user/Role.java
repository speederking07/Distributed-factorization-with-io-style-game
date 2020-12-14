package pl.zespolowe.splix.domain.user;

public enum Role {
    USER,
    ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name().toUpperCase();
    }
}
