/*
 * Author: Dinesh
 * Group: 2 | Project: P2 Bank Account System
 * File: BankTest.java
 */

/** Plain PASS/FAIL checks (no libraries). Run: java BankTest */
public class BankTest {
    private static int failures;

    public static void main(String[] args) {
        Account sav = new SavingsAccount("S1", "Ann");
        Account cur = new CurrentAccount("C1", "Ann");
        FixedDepositAccount fd = new FixedDepositAccount("F1", "Ann");

        sav.deposit(1000);
        check("Deposit adds to balance", sav.getBalance() == 1000);
        check("EDGE negative deposit rejected", !sav.deposit(-50) && sav.getBalance() == 1000);
        check("EDGE zero deposit rejected", !sav.deposit(0));
        check("EDGE savings keeps Rs.500 minimum (exact)", sav.withdraw(500) && sav.getBalance() == 500);
        check("Savings below minimum rejected", !sav.withdraw(1) && sav.getBalance() == 500);
        check("Current overdraft to -5000 allowed", cur.withdraw(5000) && cur.getBalance() == -5000);
        check("EDGE current beyond -5000 rejected", !cur.withdraw(1));

        fd.deposit(2000);
        check("Fixed deposit blocked before maturity", !fd.withdraw(100));
        fd.setMatured(true);
        check("Fixed deposit withdraw after maturity", fd.withdraw(100) && fd.getBalance() == 1900);

        Account[] all = {sav, cur, fd};
        double fees = 0;
        for (Account a : all) {
            fees += a.getMonthlyFee();
        }
        check("Polymorphic fees 25 + 100 + 0", fees == 125);

        Bank bank = new Bank();
        bank.addCustomer("1", "Ann");
        bank.addCustomer("2", "Bob");
        check("EDGE duplicate customer id rejected", !bank.addCustomer("1", "Other"));
        check("Open savings", bank.openAccount("1", 'S', "A1", 1000));
        check("EDGE duplicate account number rejected", !bank.openAccount("2", 'C', "A1", 0));
        bank.openAccount("2", 'C', "A2", 0);
        bank.openAccount("1", 'C', "A3", 0);
        bank.openAccount("1", 'F', "A4", 0);
        check("EDGE 4th account for a customer rejected", !bank.openAccount("1", 'S', "A5", 0));

        check("Failed transfer changes nothing",
                !bank.transfer("A1", "A2", 900)
                        && bank.findAccount("A1").getBalance() == 1000
                        && bank.findAccount("A2").getBalance() == 0);
        check("Transfer moves money", bank.transfer("A1", "A2", 500)
                && bank.findAccount("A2").getBalance() == 500);
        check("EDGE withdraw zero rejected", !sav.withdraw(0));
        check("EDGE transfer of a negative amount rejected",
                !bank.transfer("A1", "A2", -5)
                        && bank.findAccount("A1").getBalance() == 500
                        && bank.findAccount("A2").getBalance() == 500);
        check("EDGE mature a non-fixed account rejected", !bank.matureAccount("A1"));
        check("Mature fixed deposit", bank.matureAccount("A4")
                && bank.findAccount("A4").getBalance() == 0);
        bank.applyMonthlyFees();
        check("Bank fees and total deposits", bank.totalDeposits() == 1000 - 25 - 100 - 100);

        System.out.println(failures == 0 ? "\nAll checks passed." : "\nFailures: " + failures);
    }

    private static void check(String name, boolean condition) {
        if (!condition) {
            failures++;
        }
        System.out.println((condition ? "PASS: " : "FAIL: ") + name);
    }
}
