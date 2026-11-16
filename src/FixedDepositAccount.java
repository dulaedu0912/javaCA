/*
 * Author: Sethunima
 * Group: 2 | Project: P2 Bank Account System
 * File: FixedDepositAccount.java
 */

/** Fixed deposit: withdrawals only after the deposit has matured; no fee. */
public class FixedDepositAccount extends Account {
    private boolean matured;

    public FixedDepositAccount(String number, String holder) {
        super(number, holder);
    }

    public boolean isMatured() { return matured; }

    public void setMatured(boolean matured) { this.matured = matured; }

    @Override
    protected boolean canWithdraw(double amount) {
        return matured && amount <= getBalance();
    }

    @Override
    public double getMonthlyFee() {
        return 0;
    }
}
