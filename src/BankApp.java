/*
 * Author: Dulara
 * Group: 2 | Project: P2 Bank Account System
 * File: BankApp.java
 */

import java.util.Scanner;

/** Console menu. The only class that reads user input. */
public class BankApp {
    private static final Scanner IN = new Scanner(System.in);

    public static void main(String[] args) {
        Bank bank = new Bank();
        int choice;
        do {
            System.out.println("\n1 Add customer  2 Open account  3 Deposit  4 Withdraw  5 Transfer"
                    + "\n6 Apply monthly fees  7 Show accounts  8 Mature fixed deposit  0 Exit");
            if (!IN.hasNextLine()) {
                System.out.println("\nInput ended.");
                return;
            }
            double raw = readNumber("Choice: ");
            choice = raw == (int) raw ? (int) raw : -1;
            switch (choice) {
                case 1 -> report(bank.addCustomer(ask("Customer id: "), ask("Name: ")));
                case 2 -> report(bank.openAccount(ask("Customer id: "), typeChar(),
                        ask("New account number: "), readNumber("Opening deposit: ")));
                case 3 -> report(deposit(bank, ask("Account number: "), readNumber("Amount: ")));
                case 4 -> report(withdraw(bank, ask("Account number: "), readNumber("Amount: ")));
                case 5 -> report(bank.transfer(ask("From account: "), ask("To account: "), readNumber("Amount: ")));
                case 6 -> { bank.applyMonthlyFees(); System.out.println("Fees applied."); }
                case 7 -> System.out.println(bank.describe());
                case 8 -> report(bank.matureAccount(ask("Fixed deposit number: ")));
                case 0 -> System.out.println("Goodbye.");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private static boolean deposit(Bank bank, String number, double amount) {
        Account account = bank.findAccount(number);
        return account != null && account.deposit(amount);
    }

    private static boolean withdraw(Bank bank, String number, double amount) {
        Account account = bank.findAccount(number);
        return account != null && account.withdraw(amount);
    }

    private static void report(boolean ok) {
        System.out.println(ok ? "Done." : "Rejected: check the details and the account rules.");
    }

    private static String ask(String prompt) {
        System.out.print(prompt);
        return IN.hasNextLine() ? IN.nextLine().trim() : "";
    }

    /** First letter of S/C/F; anything else becomes 'X' and is rejected by Bank. */
    private static char typeChar() {
        String type = ask("Type (S=Savings, C=Current, F=Fixed): ");
        return type.isEmpty() ? 'X' : type.charAt(0);
    }

    /** Reads a finite number; letters, blank, NaN or Infinity give -1 so nothing crashes. */
    private static double readNumber(String prompt) {
        try {
            double value = Double.parseDouble(ask(prompt));
            return Double.isFinite(value) ? value : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
