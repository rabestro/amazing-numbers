package numbers.properties;

import numbers.natural.NaturalNumber;

import java.math.BigInteger;

public class Buzz implements NumberProperty {
    private static final BigInteger SEVEN = new BigInteger("7");

    @Override
    public boolean test(NaturalNumber number) {
        return number.mod(BigInteger.TEN).equals(SEVEN) || number.mod(SEVEN).equals(BigInteger.ZERO);
    }
}