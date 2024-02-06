package org.example.executerService8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
This setup will run  3 async threads one after another(but in parallel with main)
 */
public class WithExecutorWorflowMain {
    public static void main(String[] args) {


        // Set up a thread in a custom way for adding name(need second param = factory).
        // Thread name is rarely needed.
        ExecutorService blueExecuter = Executors.newSingleThreadExecutor(
                new ColorThreadWorkflowFactory(ThreadColor.ANSI_BLUE));

        blueExecuter.execute(WithExecutorWorflowMain::countDown);
        blueExecuter.shutdown();// executerService must be shutdown, otherwise will not stop
        boolean isDone = false;
        try {
            isDone = blueExecuter.awaitTermination(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (isDone) {
            System.out.println("Blue finished, starting Yellow");
            // Yellow executor
            ExecutorService yellowExecuter = Executors.newSingleThreadExecutor(
                    new ColorThreadWorkflowFactory(ThreadColor.ANSI_YELLOW));

            yellowExecuter.execute(WithExecutorWorflowMain::countDown);
            // executerService must be shutdown, otherwise will not stop
            // Shut down only happens after thread has either finished job or threw exception
            yellowExecuter.shutdown();

            try {
                isDone = yellowExecuter.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isDone) {
                System.out.println("Yellow finished, starting Red");
            }
            // Red executor
            ExecutorService redExecuter = Executors.newSingleThreadExecutor(
                    new ColorThreadWorkflowFactory(ThreadColor.ANSI_RED));

            redExecuter.execute(WithExecutorWorflowMain::countDown);
            redExecuter.shutdown();// executerService must be shutdown, otherwise will not stop

            try {
                isDone = redExecuter.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(isDone) {
                System.out.println("All processes completed");
            }
        }



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
