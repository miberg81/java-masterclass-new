package noExecuter.syncronizationAndLocks5.solution;

public class SynchronizationMainSolution {

    public static void main(String[] args) {

        BankAccountSolution companyAccount = new BankAccountSolution("Tom", 10000);

        Thread thread1 = new Thread(() -> companyAccount.withdraw(2500));
        Thread thread2 = new Thread(() -> companyAccount.deposit(5000));
        Thread thread3 = new Thread(() -> companyAccount.setName("Tim"));
        Thread thread4 = new Thread(() -> companyAccount.withdraw(5000));

        thread1.start();
        thread2.start();

        // to make sure 3d thread is blocked, we give thread1,2 a small head start
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        thread3.start();
        thread4.start();

        // we need to join all threads to the main thread here, so to make sure all thread finished
        // before we print the balance;
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Final Balance: " + companyAccount.getBalance());
    }
}
