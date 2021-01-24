package pl.zespolowe.splix.domain.factorization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.*;


/**
 * 1. Wybiera B <br>
 * 2. Rozbija przedział [sqrt(n), inf] na podprzedziały <br>
 * 3. Klienci szukają w podprzedziałach liczb 'b' takich, że b = (a^2 mod n) ma czynniki <= B<br>
 * Klient może zwrócić listę 'b' wraz z ich czynnikami.<br>
 * 4. Z zebranej listy 'b' z całego przedziału wybieramy podzbiory wielkości pi(B) i rozsyłamy do klientów<br>
 * 5. Klient zwraca parę (p, q) p*q=n lub błąd.
 */


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Task {
    private static int STEP = 100000;

    @Id
    private String number;

    private String B;

    private String piB;

    private String rangeStart;

    @ManyToMany
    @JoinTable(
            name = "task_fac_number",
            joinColumns = {@JoinColumn(name = "task_number")},
            inverseJoinColumns = {@JoinColumn(name = "factorized_number_b")}
    )
    @Fetch(FetchMode.SELECT)
    private Set<FactorizedNumber> numbers;

    @OneToMany(mappedBy = "task")
    @Fetch(FetchMode.SELECT)
    private Set<SubSet> subsets;

    private String factor1, factor2;


    public Task(@NonNull String number) throws NumberFormatException {
        if (!number.matches("^[0-9]+$"))
            throw new NumberFormatException("Not numerical string: " + number);
        this.number = number;

        BigInteger temp = getValue();
        double log = BigMath.logBigInteger(temp);
        double logLog = Math.log(log);
        this.B = Long.toString((long) Math.ceil(Math.exp(0.55 * Math.sqrt(log * logLog))));
        this.numbers = new HashSet<>();
        this.subsets = new HashSet<>();
        this.rangeStart = getValue().sqrt().toString();
        this.piB = this.B;
        this.factor1 = this.factor2 = "-1";
    }

    private static long newton(long n, long k) {
        long result = 1;
        long bound = n - k;
        k = 1;
        while (n > bound) {
            result *= n--;
            result /= k++;
        }
        return result;
    }

    private SubSet newSubset(int size) {
        SubSet result;
        do {
            List<FactorizedNumber> list = new LinkedList<>(numbers);
            Collections.shuffle(list);
            Set<FactorizedNumber> randomSet = new HashSet<>(list.subList(0, size));
            result = new SubSet();
            result.setNumbers(randomSet);
        } while (subsets.contains(result));
        return result;
    }

    public BigInteger getValue() {
        return new BigInteger(number);
    }

    public SubTask getSubTask() {
        SubTask result = new SubTask();
        result.setB(this.B);
        result.setPiB(this.piB);
        result.setN(this.number);
        result.setRangeMin(this.rangeStart);

        if (numbers.size() < Long.parseLong(B)) {
            rangeStart = Long.toString(Long.parseLong(rangeStart) + STEP);
            result.setRangeMax(rangeStart);
            result.setType(TaskType.PAIRS);
        } else {
            int k = subsets.stream().mapToInt(SubSet::size).max().orElseGet(() -> (int) Long.parseLong(B));
            int finalK = k;
            long count = subsets.stream().filter(s -> s.size() == finalK).count();
            if (count < newton(numbers.size(), k)) {
                result.setType(TaskType.SOLVE_LINEAR);
                SubSet subSet = newSubset(k);
                subsets.add(subSet);
                result.setFactorizedNumbers(subSet.getNumbers());
            } else if (k < numbers.size()) {
                k++;
                result.setType(TaskType.SOLVE_LINEAR);
                SubSet subSet = newSubset(k);
                subsets.add(subSet);
                result.setFactorizedNumbers(subSet.getNumbers());
            } else {
                rangeStart = Long.toString(Long.parseLong(rangeStart) + STEP);
                result.setRangeMax(rangeStart);
                result.setType(TaskType.PAIRS);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return Objects.equals(number, task.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
