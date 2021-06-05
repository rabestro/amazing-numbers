package numbers;

import java.util.StringJoiner;

class NaturalNumber {

    private String digits;
    private long number;

    NaturalNumber(final String value) {
        digits = value;
        number = Long.parseLong(value);
    }

    static long numSquareSum(long n) {
        long squareSum = 0;
        for (long i = n; i != 0; i /= 10) {
            squareSum += (i % 10) * (i % 10);
        }
        return squareSum;
    }

    void printCard() {
        System.out.printf("Properties of %,d%n", number);
        for (var property : Main.PROPERTIES) {
            final var hasProperty = test(property);
            System.out.printf("%12s: %s%n", property, hasProperty);
        }
    }

    void printLine() {
        final var properties = new StringJoiner(", ");
        for (var property : Main.PROPERTIES) {
            if (test(property)) {
                properties.add(property);
            }
        }
        System.out.printf("%,12d is %s%n", number, properties);
    }

    private boolean test(final String property) {
        var isHappy = true;
        switch (property) {
            case "even":
                return number % 2 == 0;
            case "odd":
                return number % 2 != 0;
            case "buzz":
                return number % 7 == 0 || number % 10 == 7;
            case "duck":
                return digits.indexOf('0') != -1;
            case "palindromic":
                return new StringBuilder(digits).reverse().toString().equals(digits);
            case "gapful":
                final var divider = (digits.charAt(0) - '0') * 10 + number % 10;
                return number > 100 && number % divider == 0;
            case "spy":
                long sum = 0, product = 1;
                for (long rest = number; rest > 0; rest /= 10) {
                    long digit = rest % 10;
                    product *= digit;
                    if (product == 0) {
                        return false;
                    }
                    sum += digit;
                }
                return sum == product;
            case "square":
                return Math.sqrt(number) % 1 == 0;
            case "sunny":
                return Math.sqrt(number + 1) % 1 == 0;
            case "jumping":
                for (long p = number % 10, rest = number / 10; rest > 0; rest /= 10) {
                    long c = rest % 10;
                    long d = p - c;
                    if (d != 1 && d != -1) {
                        return false;
                    }
                    p = c;
                }
                return true;
            case "sad":
                isHappy = false;
            case "happy":
                for (long i = number; i > 1; i = numSquareSum(i)) {
                    if (i == 4) {
                        return !isHappy;
                    }
                }
                return isHappy;
        }
        return false;
    }

    void increase() {
        number++;
        digits = String.valueOf(number);
    }

    boolean hasProperties(String[] query) {
        for (var property : query) {
            final var isNegative = property.charAt(0) == '-';
            if (isNegative ? test(property.substring(1)) : !test(property)) {
                return false;
            }
        }
        return true;
    }

}