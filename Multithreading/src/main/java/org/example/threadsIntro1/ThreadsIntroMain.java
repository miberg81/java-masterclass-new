package org.example.threadsIntro1;

import java.util.concurrent.TimeUnit;

public class ThreadsIntroMain {
    public static void main(String[] args) {
        var currentThread = Thread.currentThread();
        System.out.println(currentThread);

        // First way to create thread
        CustomThread customThread = new CustomThread();
        customThread.start();

        // Second way to create thread
        Runnable myRunnable = () -> {
            for (int i = 0; i < 8; i++) {
                System.out.println(" custom with lambda  ");
                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(myRunnable).start();

        for (int i = 0; i < 3; i++) {
            System.out.println(" main ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                    throw new RuntimeException(e);
            }
        }
    }

    public static void printThreadState(Thread thread) {
        System.out.println("=========================");
        System.out.println("Thread ID " + thread.getId());
        System.out.println("Thread Name" + thread.getName());
        System.out.println();
    }
}