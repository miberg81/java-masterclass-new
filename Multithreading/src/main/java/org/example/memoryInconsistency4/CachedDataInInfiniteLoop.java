package org.example.memoryInconsistency4;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

public class CachedDataInInfiniteLoop {
    private boolean flag = false;

    public void toggleFlag() {
        flag = !flag;
    }

    public boolean getFlag(){
        return  flag;
    }

    public static void main(String[] args) {
        CachedDataInInfiniteLoop cachedData = new CachedDataInInfiniteLoop();

        Thread writerThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // writer thread updates the shared flag on the heap
            // but the reader thread still holds the older cached value in his own memory
            // This is why the reader is stuck in the loop forever
            cachedData.toggleFlag();
            System.out.println("A. Flag set to " + cachedData.getFlag());
        });

        Thread readerThread = new Thread(() -> {
            while (!cachedData.getFlag()) { // flag if false
                // writer thread is still busy, keep waiting in the loop...
            }
            System.out.println("B. Flag is " + cachedData.getFlag());
        });

        writerThread.start();
        readerThread.start();
    }
}
