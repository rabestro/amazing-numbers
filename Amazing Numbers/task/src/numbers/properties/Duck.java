package numbers.properties;

import numbers.natural.NaturalNumber;

public class Duck implements NumberProperty {

    @Override
    public boolean test(NaturalNumber number) {
        return number.toString().indexOf('0') >= 0;
    }
}