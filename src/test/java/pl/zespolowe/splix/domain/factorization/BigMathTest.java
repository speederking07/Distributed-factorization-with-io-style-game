package pl.zespolowe.splix.domain.factorization;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class BigMathTest {


    @Test
    public void logTest() {
        BigInteger integer = new BigInteger("10000");
        double result = BigMath.logBigInteger(integer);
        assertTrue(result >= 9.21);
        assertTrue(result <= 9.2104);
    }

}
