package org.example.interratctingWithThread2;

public class InterractMain {
    public static void main(String[] args) {

        System.out.println("Main thread running");
        try {
            System.out.println("Main thread paused for one second");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // downloads installation package
        Thread downloadPackageThread = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " should take 10 dots to run.");
            for (int i = 0; i < 10; i++) {
                System.out.print(". ");
                try {
                    Thread.sleep(500);
                    //System.out.println("A. State = " + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    System.out.println("\n Whoops!! " + threadName + " interrupted ");
                    Thread.currentThread().interrupt();
                    return; // exit loop
                }
            }
            System.out.println("\n" + threadName + " completed ");
        }, "downloadPackageThread");

        // only start this thread if installation thread finished
        Thread installThread = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(250);
                    System.out.println("Installation step " + (i+1) + " is completed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "InstallThread");

        // only interrupt the second thread if it's taking longer than 3 seconds;
        Thread downloadPackageMonitorThread = new Thread(()->{
            long now = System.currentTimeMillis();
            while (downloadPackageThread.isAlive()) {
                try {
                    Thread.sleep(1000);

                    if (System.currentTimeMillis() - now > 2000) {
                        downloadPackageThread.interrupt();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "threadMonitorThread");

        System.out.println(downloadPackageThread.getName() + " starting");
        downloadPackageThread.start();
        downloadPackageMonitorThread.start();

        // joins downloadInstallPackageThread to the current thread(Main)
        // the main thread will wait here until the downloadInstallPackageThread completes
        try {
            downloadPackageThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!downloadPackageThread.isInterrupted()) {
            installThread.start();
        } else {
            System.out.println("previous thread  was interrupted, "+
                    installThread.getName() + "can't run");
        }
    }
}
