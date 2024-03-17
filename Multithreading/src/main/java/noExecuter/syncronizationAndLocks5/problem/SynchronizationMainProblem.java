package noExecuter.syncronizationAndLocks5.problem;

public class SynchronizationMainProblem {

    public static void main(String[] args) {

        BankAccounProblem companyAccount = new BankAccounProblem(10000);

        Thread thread1 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(() -> companyAccount.deposit(5000));
        Thread thread3 = new Thread(() -> companyAccount.withdraw(2500));

        thread1.start();
        thread2.start();
        thread3.start();

        // we need to join all threads to the main thread here, so to make sure all thread finished
        // before we print the balance;
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Final Balance: " + companyAccount.getBalance());
    }
}
