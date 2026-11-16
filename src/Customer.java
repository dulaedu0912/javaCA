/*
 * Author: Ishini
 * Group: 2 | Project: P2 Bank Account System
 * File: Customer.java
 */

/** A bank customer holding at most 3 accounts in a fixed-size array. */
public class Customer {
    private static final int MAX_ACCOUNTS = 3;

    private final String id;
    private final String name;
    private final Account[] accounts = new Account[MAX_ACCOUNTS];
    private int accountCount;

    public Customer(String id, String name) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Id and name are required");
        }
        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() { return id; }

    public String getName() { return name; }

    public boolean isFull() { return accountCount == MAX_ACCOUNTS; }

    public boolean addAccount(Account account) {
        if (account == null || isFull()) {
            return false;
        }
        accounts[accountCount++] = account;
        return true;
    }

    public Account findAccount(String number) {
        if (number == null) {
            return null;
        }
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getNumber().equals(number.trim())) {
                return accounts[i];
            }
        }
        return null;
    }

    public double totalBalance() {
        double total = 0;
        for (int i = 0; i < accountCount; i++) {
            total += accounts[i].getBalance();
        }
        return total;
    }

    /** Charges every account its own monthly fee (polymorphic call). */
    public void applyMonthlyFees() {
        for (int i = 0; i < accountCount; i++) {
            accounts[i].applyMonthlyFee();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Customer " + id + " - " + name + "\n");
        for (int i = 0; i < accountCount; i++) {
            sb.append("   ").append(accounts[i]).append('\n');
        }
        return sb.toString();
    }
}
