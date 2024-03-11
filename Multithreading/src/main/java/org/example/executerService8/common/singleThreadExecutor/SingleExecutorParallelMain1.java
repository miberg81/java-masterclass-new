package org.example.executerService8.common.singleThreadExecutor;

import org.example.executerService8.common.ThreadColor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Use multiple SingleThreadExecutors to create multi threaded program
 * In this example 3 threads are created, run in paralell
 * (don't wait for each other)
 */
public class SingleExecutorParallelMain1 {
    public static void main(String[] args) {

        //This is the defualt setup(not thread name)
        // ExecutorService blueExecuter = Executors.newSingleThreadExecutor();

        // Set up a thread in a custom way for adding name(need second param = factory).
        // Thread name is rarely needed.
        ExecutorService blueExecuter = Executors.newSingleThreadExecutor(
                new ColorThreadFactoryCustomColor(ThreadColor.ANSI_BLUE));

        blueExecuter.execute(SingleExecutorParallelMain1::countDown);
        blueExecuter.shutdown();// executerService must be shutdown, otherwise will not stop

        // Yellow executor
        ExecutorService yellowExecuter = Executors.newSingleThreadExecutor(
                new ColorThreadFactoryCustomColor(ThreadColor.ANSI_YELLOW));

        yellowExecuter.execute(SingleExecutorParallelMain1::countDown);
        // executerService must be shutdown, otherwise will not stop
        // Shut down only happens after thread has either finished job or threw exception
        yellowExecuter.shutdown();

        // Red executor
        ExecutorService redExecuter = Executors.newSingleThreadExecutor(
                new ColorThreadFactoryCustomColor(ThreadColor.ANSI_RED));

        redExecuter.execute(SingleExecutorParallelMain1::countDown);
        redExecuter.shutdown();// executerService must be shutdown, otherwise will not stop

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
