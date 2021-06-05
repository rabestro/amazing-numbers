package numbers;

// A very simple solution using only the most basic Java classes.

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

public final class Main {
    static final String[] PROPERTIES = new String[]{
            "even", "odd", "buzz", "duck", "palindromic", "gapful",
            "spy", "square", "sunny", "jumping", "happy", "sad"
    };
    private static final String HELP = String.join("\n ",
            "Supported requests:",
            "- enter a natural number to know its properties;",
            "- enter two natural numbers to obtain the properties of the list:",
            "  * the first parameter represents a starting number;",
            "  * the second parameter shows how many consecutive numbers are to be processed;",
            "- two natural numbers and a property to search for;",
            "- two natural numbers and properties to search for;",
            "- a property preceded by minus must not be present in numbers;",
            "- separate the parameters with one space;",
            "- enter 0 to exit."
    );
    private static final String[][] EXCLUSIVE = new String[][]{
            {"even", "odd"}, {"spy", "duck"}, {"sunny", "square"}, {"happy", "sad"},
            {"-even", "-odd"}, {"-happy", "-sad"}
    };
    private static final String[][] MUTUALLY_EXCLUSIVE = new String[EXCLUSIVE.length + PROPERTIES.length][];
    private static final Scanner scanner = new Scanner(System.in);

    static {
        Arrays.sort(PROPERTIES);
        int index = 0;
        for (var pair : EXCLUSIVE) {
            MUTUALLY_EXCLUSIVE[index++] = pair;
        }
        for (var property : PROPERTIES) {
            MUTUALLY_EXCLUSIVE[index++] = new String[]{property, "-" + property};
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println(HELP);
        run();
        System.out.println("Goodbye!");
    }

    private static boolean notNatural(final String value) {
        for (var symbol : value.toCharArray()) {
            if (!Character.isDigit(symbol)) {
                return true;
            }
        }
        return "0".equals(value);
    }

    static boolean isWrong(final String property) {
        return Arrays.binarySearch(PROPERTIES, property) < 0;
    }

    static String getWrongProperties(final String[] query) {
        var wrong = new StringJoiner(", ");
        for (var property : query) {
            var name = property.charAt(0) == '-' ? property.substring(1) : property;
            if (isWrong(name)) {
                wrong.add(property);
            }
        }
        return wrong.toString();
    }

    static String getMutuallyExclusive(final String[] query) {
        Arrays.sort(query);
        var me = new StringJoiner(", ");
        for (var pair : MUTUALLY_EXCLUSIVE) {
            var containsPair = Arrays.binarySearch(query, pair[0]) >= 0 && Arrays.binarySearch(query, pair[1]) >= 0;
            if (containsPair) {
                me.add(pair[0] + " and " + pair[1]);
            }
        }
        return me.toString();
    }

    private static void run() {
        while (true) {
            final var request = readRequest();

            if ("0".equals(request[0])) {
                return;
            }
            if (notNatural(request[0])) {
                System.out.println("The first parameter should be a natural number or zero.");
                continue;
            }
            final var number = new NaturalNumber(request[0]);
            if (request.length == 1) {
                number.printCard();
                continue;
            }
            if (notNatural(request[1])) {
                System.out.println("The second parameter should be a natural number.");
                continue;
            }
            int count = Integer.parseInt(request[1]);
            final var query = request.length == 3 ? request[2].split(" ") : new String[0];
            final var wrong = getWrongProperties(query);
            if (!wrong.isBlank()) {
                System.out.printf(wrong.contains(", ") ?
                        "The properties %s are wrong" : "The property %s is wrong", wrong);
                System.out.println("Available properties: ");
                System.out.println(Arrays.toString(PROPERTIES));
                continue;
            }
            final var mutuallyExclusivePairs = getMutuallyExclusive(query);

            if (!mutuallyExclusivePairs.isBlank()) {
                System.out.print("The request contains mutually exclusive properties: ");
                System.out.println(mutuallyExclusivePairs);
                System.out.println("There are no numbers with these properties.");
                continue;
            }
            for (; count > 0; number.increase()) {
                if (number.hasProperties(query)) {
                    number.printLine();
                    count--;
                }
            }
        }
    }

    private static String[] readRequest() {
        System.out.println();
        System.out.print("Enter a request: ");
        final var input = scanner.nextLine().toLowerCase();
        System.out.println();
        return input.split(" ", 3);
    }

}