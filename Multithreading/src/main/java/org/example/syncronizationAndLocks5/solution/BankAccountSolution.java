package org.example.syncronizationAndLocks5.solution;

public class BankAccountSolution {

    private String name;

    // adding volatile alone here does not fix the problem!
    // because it only fixes memory inconsistency, but not atomicity(thread interference)
    // because the balance is changed in multiple methods
    // one task might be subtracting at the same time another is adding
    // and because += operator and double values are not atomic
    private  double balance ;

    private final Object lockName = new Object();
    private final Object lockBalance = new Object();

    public BankAccountSolution(String name, double balance){
        this.name = name;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        synchronized (this.lockName) {
            this.name = name;
            System.out.println("Updated name = " + this.name);
        }
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        try {
            System.out.println("Deposit - Talking to the teller at the bank");
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (lockBalance) {
            double originalBalance = balance;
            balance += amount;
            System.out.printf("STARTING BALANCE: %.0f , DEPOSIT (%.0f)" +
                    " : NEW BALANCE = %.0f%n", originalBalance, amount, balance);
            // one thread has acquired the lock already 9so others cant aenter this block
            // so here this thread is calling another method, which also asks for
            // the same lock, which has already been granted.
            addPromoDollars(amount);
        }
    }

    // This code is called by the same thread
    private void addPromoDollars(double amount) {
        // when somebody's balance is below 500 he is qualified for this promotion
        if (amount >= 5000) {
            // thread asking for the same lock it already has!
            // This feature is reentrant synchronization
            synchronized (lockBalance){
                System.out.println("Congratulations, you earned a promotional deposit.");
                balance +=25;
            }
        }
    }

    // synchronized method (also lock on BankAccount)
    public synchronized void withdraw(double amount) {
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
