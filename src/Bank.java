/*
 * Author: Srinath
 * Group: 2 | Project: P2 Bank Account System
 * File: Bank.java
 */

/** Holds all customers and applies the bank-wide business rules. */
public class Bank {
    private static final int MAX_CUSTOMERS = 50;

    private final Customer[] customers = new Customer[MAX_CUSTOMERS];
    private int customerCount;

    public boolean addCustomer(String id, String name) {
        if (customerCount == MAX_CUSTOMERS || findCustomer(id) != null) {
            return false;
        }
        try {
            customers[customerCount] = new Customer(id, name);
            customerCount++;
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Customer findCustomer(String id) {
        if (id == null) {
            return null;
        }
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getId().equals(id.trim())) {
                return customers[i];
            }
        }
        return null;
    }

    /** Searches every customer, so account numbers are unique bank-wide. */
    public Account findAccount(String number) {
        for (int i = 0; i < customerCount; i++) {
            Account found = customers[i].findAccount(number);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /** type: S = savings, C = current, F = fixed deposit. */
    public boolean openAccount(String customerId, char type, String number, double initialDeposit) {
        Customer owner = findCustomer(customerId);
        if (owner == null || owner.isFull() || initialDeposit < 0
                || number == null || number.isBlank() || findAccount(number) != null) {
            return false;
        }
        String holder = owner.getName();
        Account account = switch (Character.toUpperCase(type)) {
            case 'S' -> new SavingsAccount(number, holder);
            case 'C' -> new CurrentAccount(number, holder);
            case 'F' -> new FixedDepositAccount(number, holder);
            default -> null;
        };
        if (account == null) {
            return false;
        }
        if (initialDeposit > 0) {
            account.deposit(initialDeposit);
        }
        return owner.addAccount(account);
    }

    /** Money moves only if the withdrawal succeeds; otherwise nothing changes. */
    public boolean transfer(String from, String to, double amount) {
        Account source = findAccount(from);
        Account target = findAccount(to);
        if (source == null || target == null || source == target) {
            return false;
        }
        return source.withdraw(amount) && target.deposit(amount);
    }

    public boolean matureAccount(String number) {
        if (findAccount(number) instanceof FixedDepositAccount fixed) {
            fixed.setMatured(true);
            return true;
        }
        return false;
    }

    public void applyMonthlyFees() {
        for (int i = 0; i < customerCount; i++) {
            customers[i].applyMonthlyFees();
        }
    }

    public double totalDeposits() {
        double total = 0;
        for (int i = 0; i < customerCount; i++) {
            total += customers[i].totalBalance();
        }
        return total;
    }

    public String describe() {
        if (customerCount == 0) {
            return "No customers yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customerCount; i++) {
            sb.append(customers[i]);
        }
        return sb.append(String.format("Total deposits: Rs.%.2f", totalDeposits())).toString();
    }
}
