/*
 * Author: Avishka
 * Group: 2 | Project: P2 Bank Account System
 * File: SavingsAccount.java
 */

/** Savings: Rs. 500 minimum balance must remain; monthly fee Rs. 25. */
public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500;
    private static final double FEE = 25;

    public SavingsAccount(String number, String holder) {
        super(number, holder);
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return getBalance() - amount >= MIN_BALANCE;
    }

    @Override
    public double getMonthlyFee() {
        return FEE;
    }
}
