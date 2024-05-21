import java.util.Scanner;
import java.lang.NumberFormatException;


public class Contador {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int start = askInt(
                scanner, "(Integer interval) Inclusive lower bound: "
            );
            int end = askInt(
                scanner, "(Integer interval) Inclusive upper bound: "
            );
            counter(start, end);
        }
    }

    // Adaptation from lab-syntax
    private static int askInt(Scanner scanner, String message) {
        int value;
        while (true) {
            try {
                System.out.print(message);
                value = Integer.parseInt(scanner.nextLine().trim());
                break;
            }
            catch (NumberFormatException e) {
                continue;
            }
        }
        return value;
    }

    // Line wrapping in Java is strange. How can I wrap this method signature?
    private static void counter(int start, int end) throws MalformedIntervalException {
        if (end < start) { 
            throw new MalformedIntervalException(
                "lower bound greater than upper bound."
            );
        }
        for (; start <= end; start++) {
            System.out.println(start);
        }
    }
}


class MalformedIntervalException extends Exception {
    public MalformedIntervalException(String message) {
        super(message);
    }
}
