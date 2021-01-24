package pl.zespolowe.splix.calculations;

import org.junit.Assert;
import org.junit.Test;
import java.math.BigInteger;

/***
 *
 * @author KalinaMichal
 *
 * Test for PrimeGenerator
 */
public class PrimeGeneratorTest {
    /***
     * main()
     */
    @Test
    public void generatePrime() {
        //given
        PrimeGenerator sut = new PrimeGenerator();

        //when
        BigInteger test = sut.main();

        //then
        Assert.assertEquals(test.compareTo(BigInteger.valueOf(10000)),1);
    }
}
