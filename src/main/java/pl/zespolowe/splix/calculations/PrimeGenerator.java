package pl.zespolowe.splix.calculations;

import java.math.BigInteger;
import java.util.Random;

/***
 * Sub program for primes
 * @Author KalinaMichal
 *
 */

public class PrimeGenerator {
    public static void main(String[] args) {
        for(int i =0;i<20;i++) {
            BigInteger a = new BigInteger(50, 1, new Random());
            BigInteger b = new BigInteger(50, 1, new Random());
            //System.out.println(a);
            //System.out.println(b);
            System.out.println(a.multiply(b));
        }
    }
}
