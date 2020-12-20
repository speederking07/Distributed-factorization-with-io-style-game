package pl.zespolowe.splix.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    private String token;

    @ManyToOne
    private User user;

    @Basic
    private Date validTo;

    public boolean isValid() {
        return Calendar.getInstance().getTime().getTime() <= validTo.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecoveryToken)) return false;
        RecoveryToken that = (RecoveryToken) o;
        return Objects.equals(token, that.token) && Objects.equals(user, that.user) && Objects.equals(validTo, that.validTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, user, validTo);
    }
}
