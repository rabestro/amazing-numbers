package numbers.properties;

import numbers.natural.NaturalNumber;

import java.math.BigInteger;

public class Odd implements NumberProperty {

    @Override
    public boolean test(NaturalNumber number) {
        return number.mod(BigInteger.TWO).equals(BigInteger.ONE);
    }
}