/*
 * Author: Dumaya
 * Group: 2 | Project: P2 Bank Account System
 * File: CurrentAccount.java
 */

/** Current: overdraft allowed down to -Rs. 5000; monthly fee Rs. 100. */
public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 5000;
    private static final double FEE = 100;

    public CurrentAccount(String number, String holder) {
        super(number, holder);
    }

    @Override
    protected boolean canWithdraw(double amount) {
        return getBalance() - amount >= -OVERDRAFT_LIMIT;
    }

    @Override
    public double getMonthlyFee() {
        return FEE;
    }
}
