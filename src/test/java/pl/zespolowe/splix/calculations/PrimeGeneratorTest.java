package pl.zespolowe.splix.calculations;

import org.junit.Assert;
import org.junit.Test;
import java.math.BigInteger;

public class PrimeGeneratorTest {
    @Test
    public void generatePrime() {
        //given
        PrimeGenerator sut = new PrimeGenerator();

        //when
        BigInteger test = sut.main();

        //then
        Assert.assertTrue(test.compareTo(BigInteger.valueOf(10000))==1);
    }
}
