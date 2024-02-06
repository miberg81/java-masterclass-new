package org.example.executerService8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Set up thread with executer, but in a custom way(allows to add thread name)
 * Factory is needed as a seconf param for the executor
 */
class ColorThreadFactory implements ThreadFactory {

    private String threadName;

    public ColorThreadFactory(ThreadColor color) {
        this.threadName = color.name();
    }

    /*
    Factory method to create a thread in a custom way.
     */
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }
}

/**
 * Use multiple SingleThreadExecutors to create multi threaded program
 */
public class WithExecutorParallelMain {
    public static void main(String[] args) {

        //This is the defualt setup(not thread name)
        // ExecutorService blueExecuter = Executors.newSingleThreadExecutor();

        // Set up a thread in a custom way for adding name(need second param = factory).
        // Thread name is rarely needed.
        ExecutorService blueExecuter = Executors.newSingleThreadExecutor(
                new ColorThreadWorkflowFactory(ThreadColor.ANSI_BLUE));

        blueExecuter.execute(WithExecutorParallelMain::countDown);
        blueExecuter.shutdown();// executerService must be shutdown, otherwise will not stop

        // Yellow executor
        ExecutorService yellowExecuter = Executors.newSingleThreadExecutor(
                new ColorThreadWorkflowFactory(ThreadColor.ANSI_YELLOW));

        yellowExecuter.execute(WithExecutorParallelMain::countDown);
        // executerService must be shutdown, otherwise will not stop
        // Shut down only happens after thread has either finished job or threw exception
        yellowExecuter.shutdown();

        // Red executor
        ExecutorService redExecuter = Executors.newSingleThreadExecutor(
                new ColorThreadWorkflowFactory(ThreadColor.ANSI_RED));

        redExecuter.execute(WithExecutorParallelMain::countDown);
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
