package pl.zespolowe.splix.domain.factorization;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
public class FactorizedNumber {
    @Id
    private String num;

    private String a;

    @ElementCollection
    @MapKeyColumn(name = "factor")
    @Column(name = "power")
    @CollectionTable(name = "factors", joinColumns = @JoinColumn(name = "factorized_number_b"))
    @Fetch(FetchMode.SELECT)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Map<String, String> factors;

    public FactorizedNumber() {
        this.factors = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FactorizedNumber)) return false;
        FactorizedNumber that = (FactorizedNumber) o;
        return Objects.equals(num, that.num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }
}
