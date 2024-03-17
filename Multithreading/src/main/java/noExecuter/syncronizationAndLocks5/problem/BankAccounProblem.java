package noExecuter.syncronizationAndLocks5.problem;

public class BankAccounProblem {

    // errors to printing could be due to balance caching(memory inconsistency)
    // or threads interference
    // or both
    private double balance ;

    public BankAccounProblem(double balance){
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        try {
            Thread.sleep(100); // mocking physical money deposit wait
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double originalBalance = balance;
        balance += amount;
        System.out.printf("STARTING BALANCE: %.0f , DEPOSIT (%.0f)" +
                " : NEW BALANCE = %.0f%n", originalBalance, amount, balance);
    }

    public void withdraw(double amount) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        double originalBalance = balance;
        if (amount <= balance) {
            balance -= amount;
            System.out.printf("STARTING BALANCE: %.0f , WITHDRAWAL (%.0f)" +
                    " : NEW BALANCE = %.0f%n", originalBalance, amount, balance);
        } else {
            System.out.printf("STARTING BALANCE: %.0f , WITHDRAWAL (%.0f)" +
                    " : INSUFFICIENT FUNDS!", originalBalance, amount, balance);
        }
    }


}
