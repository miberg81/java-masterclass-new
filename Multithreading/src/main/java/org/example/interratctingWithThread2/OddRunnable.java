package org.example.interratctingWithThread2;

public class OddRunnable implements Runnable {

    @Override
    public void run() {
        //System.out.println("I'm odd thread. Printing odd numbers");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("odd thread was interrupted");
                Thread.currentThread().interrupt();
                break;
            }
            if (this.isOdd(i)) {
                System.out.println("odd thread " + i);
            }
        }
    }

    private boolean isOdd(int i) {
        return (i % 2 != 0);
    }
}
