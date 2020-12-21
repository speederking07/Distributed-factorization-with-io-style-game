package pl.zespolowe.splix.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RecoveryToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotBlank
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Basic
    private Date validTo;

    public static RecoveryToken generateToken() {
        RecoveryToken token = new RecoveryToken();
        token.setToken(UUID.randomUUID().toString());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        token.setValidTo(calendar.getTime());

        return token;
    }

    public boolean isValid() {
        return Calendar.getInstance().getTime().getTime() <= validTo.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecoveryToken)) return false;
        RecoveryToken that = (RecoveryToken) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
