package noExecuter.interratctingWithThread2;

public class ThreadChallengeMain {
    public static void main(String[] args) {
        EvenThread evenThread = new EvenThread();

        Runnable OddRunnable = new OddRunnable();
        Thread oddThread = new Thread(OddRunnable);
        evenThread.start();
        oddThread.start();

        // put main thread to sleep so, other 2 threads can do some work
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        oddThread.interrupt();
//        long now = System.currentTimeMillis();
//        while(oddThread.isAlive()) {
//                //Thread.sleep(200);
//                if(System.currentTimeMillis()-now>2000) {
//                    oddThread.interrupt();
//                }
//        }
    }
}
