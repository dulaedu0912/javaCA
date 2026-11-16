/*
 * Author: Arkam
 * Group: 2 | Project: P2 Bank Account System
 * File: Account.java
 */

/** Base class for all accounts. Subclasses decide withdrawal rules and fees. */
public abstract class Account implements Transactable {
    private final String number;
    private final String holder;
    private double balance;

    public Account(String number, String holder) {
        if (number == null || number.isBlank() || holder == null || holder.isBlank()) {
            throw new IllegalArgumentException("Number and holder are required");
        }
        this.number = number.trim();
        this.holder = holder.trim();
    }

    public String getNumber() { return number; }

    public String getHolder() { return holder; }

    public double getBalance() { return balance; }

    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0 || !canWithdraw(amount)) {
            return false;
        }
        balance -= amount;
        return true;
    }

    /** Account-specific rule: may this amount be withdrawn right now? */
    protected abstract boolean canWithdraw(double amount);

    public abstract double getMonthlyFee();

    /** Charges the monthly fee, even if it pushes the balance below a limit. */
    public void applyMonthlyFee() {
        balance -= getMonthlyFee();
    }

    @Override
    public String toString() {
        return String.format("%s [%s] holder=%s balance=Rs.%.2f fee=Rs.%.2f",
                getClass().getSimpleName(), number, holder, balance, getMonthlyFee());
    }
}
