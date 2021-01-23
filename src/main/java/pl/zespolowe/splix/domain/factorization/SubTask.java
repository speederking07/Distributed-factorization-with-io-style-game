package pl.zespolowe.splix.domain.factorization;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SubTask {
    private TaskType type;
    private String n;
    private String B, piB, rangeMin, rangeMax;
    private Set<FactorizedNumber> factorizedNumbers;

    public SubTask() {
        this.factorizedNumbers = new HashSet<>();
    }
}
