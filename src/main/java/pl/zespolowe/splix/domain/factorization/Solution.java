package pl.zespolowe.splix.domain.factorization;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Solution {
    @NonNull
    private TaskType type;
    private Set<FactorizedNumber> factorizedNumbers;
    private String n, p, q;
}
