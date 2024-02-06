package org.example.executerService8;

/**
 * Set up where main only single color-thread can run (with the orthers waiting)
 * But the main thread can still do something in parallel
 */
public class NoExecutorMain {
    public static void main(String[] args) {
        Thread blue = new Thread(NoExecutorMain::countDown, ThreadColor.ANSI_BLUE.name());
        Thread yellow = new Thread(NoExecutorMain::countDown, ThreadColor.ANSI_YELLOW.name());
        Thread red = new Thread(NoExecutorMain::countDown, ThreadColor.ANSI_RED.name());

        // this order of start is not guaranteed
        blue.start();
        try {
            blue.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        yellow.start();
        try {
            yellow.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        red.start();
        try {
            red.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // join to the main when thread is finished (again order of join is not guaranteed


    }

    private static void countDown() {
        String threadName = Thread.currentThread().getName();
        var threadColor = ThreadColor.ANSI_RESET; // default if user did not set...
        try {
            threadColor = ThreadColor.valueOf(threadName.toUpperCase());
        } catch (IllegalArgumentException e) {

        }
        String color = threadColor.color();
        for (int i = 20; i >= 0; i--) {
            System.out.println(color + " " +
                    threadName.replace("ANSI_", "") +
                    " " + i);
        }
    }
}
