package org.example.interratctingWithThread2;

/*
Your task is to create two threads.
You should make one thread subclass the java.lang.Thread class.

The other should be created using a Runnable, which you can pass to the Thread constructor.
This can be any class that implements Runnable, or a lambda expression.

Each thread should have a run method.  The first thread's code should print 5 even numbers,
and the second thread should print 5 odd numbers.

You should execute them asynchronously, calling the start method on each,
in two consecutive statements, in your main code.
Have your main method, after these threads run a few seconds, interrupt one or both of these threads.

 */
public class EvenThread extends Thread{
    @Override
    public void run() {
        //System.out.println("Hi, I'm even thread, printing even numbers...");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            if(this.isEven(i)){
                System.out.println("even thread " + i);
            }
        }

    }

    private boolean isEven(int i) {
        return (i%2==0);
    }
}

