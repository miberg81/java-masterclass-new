package org.example.executerService8.common.multiThreadsExecutorFixedThreadPool;

import org.example.executerService8.common.ThreadColor;

import java.util.concurrent.Executors;

/*
Execute 6 tasks, but reusing a pool of 3 threads
 */
public class MultipleThreadsExecutor3 {

    public static void main(String[] args) {
        // amount of task to do! (task is defined in the runnable)
        // this number may or may not be equal to the threads amount
        // In the next example a pool of 3 threads are created to perform 6 tasks,
        // First 3 threads executed the first 3 tasks, then, the same
        // 3 threads(with same colors) were reused to execute the second 3 tasks.
        int count = 6;

        // The unique color is given when thread is created,
        // So when 3 threads created, only 3 colors exist
        var multiExecutor = Executors.newFixedThreadPool(
                3, new ColorThreadFactoryAutomaticColor());

        // create 3 threads with different colors.
        for (int i = 0; i < count; i++) {
            multiExecutor.execute(MultipleThreadsExecutor3::countDown);
        }
        multiExecutor.shutdown();
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


