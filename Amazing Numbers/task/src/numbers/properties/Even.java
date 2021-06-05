package numbers.properties;

import numbers.natural.NaturalNumber;

import java.math.BigInteger;

public class Even implements NumberProperty {

    @Override
    public boolean test(NaturalNumber number) {
        return number.mod(BigInteger.TWO).equals(BigInteger.ZERO);
    }
}
