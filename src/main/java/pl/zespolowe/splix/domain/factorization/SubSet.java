package pl.zespolowe.splix.domain.factorization;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class SubSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToMany
    @JoinTable(
            name = "subset_fac_number",
            joinColumns = {@JoinColumn(name = "sub_number")},
            inverseJoinColumns = {@JoinColumn(name = "factorized_number_b")}
    )
    @Fetch(FetchMode.SELECT)
    private Set<FactorizedNumber> numbers;

    @ManyToOne
    private Task task;

    public SubSet() {
        this.numbers = new HashSet<>();
    }


    public int size() {
        return numbers.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubSet)) return false;
        SubSet subSet = (SubSet) o;
        return Objects.equals(numbers, subSet.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers);
    }
}
