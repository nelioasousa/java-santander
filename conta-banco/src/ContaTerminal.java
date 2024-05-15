import java.util.Scanner;
import java.lang.NumberFormatException;

public class ContaTerminal {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        try {
            // Doesn't make much sense! But there it is...
            int accountNumber = ContaTerminal.askInt("Número da conta: ");
            String bankBranch = ContaTerminal.askText("Agência: ");
            String fullName = ContaTerminal.askText("Nome: ");
            double accountBalance = ContaTerminal.askDouble("Saldo: ");
            System.out.printf(
                "%nOlá %s, obrigado por criar uma conta em nosso banco,"
                + " sua agência é %s, conta %s e seu saldo"
                + " %.2f já está disponível para saque.%n",
                fullName, bankBranch, accountNumber, accountBalance);
        } finally {
            ContaTerminal.scanner.close();
        }
    }
    
    private static int askInt(String message) {
        int value;
        while (true) {
            try {
                System.out.print(message);
                value = Integer.parseInt(
                    ContaTerminal.scanner.nextLine().trim());
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
                    ContaTerminal.scanner.nextLine().trim());
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
            text = ContaTerminal.scanner.nextLine().trim();
        } while (text.length() == 0);
        return text;
    }
}
