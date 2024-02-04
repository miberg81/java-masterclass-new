package org.example.threadsIntro1;

public class CustomThread extends Thread{
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(" custom with extend");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
