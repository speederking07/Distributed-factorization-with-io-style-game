package pl.zespolowe.splix.domain;

public enum Role {
    GUEST,
    USER,
    ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name().toUpperCase();
    }
}
