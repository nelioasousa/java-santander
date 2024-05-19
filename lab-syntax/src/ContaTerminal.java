import java.util.Scanner;
import java.lang.NumberFormatException;

public class ContaTerminal {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        try {
            // Doesn't make much sense! But there it is...
            int accountNumber = askInt("Número da conta: ");
            String bankBranch = askText("Agência: ");
            String fullName = askText("Nome: ");
            double accountBalance = askDouble("Saldo: ");
            System.out.printf(
                "%nOlá %s, obrigado por criar uma conta em nosso banco,"
                + " sua agência é %s, conta %s e seu saldo"
                + " %.2f já está disponível para saque.%n",
                fullName, bankBranch, accountNumber, accountBalance);
        } finally {
            scanner.close();
        }
    }
    
    private static int askInt(String message) {
        int value;
        while (true) {
            try {
                System.out.print(message);
                value = Integer.parseInt(
                    scanner.nextLine().trim());
                break;
            }
            catch (NumberFormatException e) {
                continue;
            }
        }
        return value;
    }

    private static double askDouble(String message) {
        double value;
        while (true) {
            try {
                System.out.print(message);
                value = Double.parseDouble(
                    scanner.nextLine().trim());
                break;
            }
            catch (NumberFormatException e) {
                continue;
            }
        }
        return value;
    }

    private static String askText(String message) {
        String text;
        do {
            System.out.print(message);
            text = scanner.nextLine().trim();
        } while (text.length() == 0);
        return text;
    }
}
