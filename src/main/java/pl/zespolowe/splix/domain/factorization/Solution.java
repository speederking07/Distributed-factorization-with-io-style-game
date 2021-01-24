package pl.zespolowe.splix.domain.factorization;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Solution {
    @NonNull
    private TaskType type;
    @NonNull
    private String n;
    private Set<FactorizedNumber> factorizedNumbers;
    private String p, q;

    public Solution() {
        this.n = this.p = this.q = "";
        this.factorizedNumbers = new HashSet<>();
    }
}
