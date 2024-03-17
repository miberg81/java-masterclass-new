package noExecuter.sharedMemoryStopwatch3;

import java.util.concurrent.TimeUnit;

/*
In this main no objects are shared (each thread is separate)
 */
public class ThreadColorSharedMain {
    public static void main(String[] args) {

        // This instance of stopwatch is shared by all threads
        // This time it becomes a problem because if the fields i it has.
        StopWatchShared stopWatchShared = new StopWatchShared(TimeUnit.SECONDS);
        Thread green = new Thread(stopWatchShared::countDown, ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread(() -> stopWatchShared.countDown(7), ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(stopWatchShared::countDown, ThreadColor.ANSI_RED.name());

        green.start();
        purple.start();
        red.start();
    }
}

/*
Stop watch counts time in the timeUnit specified (like minutes, seconds etc)
 */
class StopWatchShared {
    private TimeUnit timeUnit;

    private int i; //!!!!!!!!! THIS VAR WILL BE SHARED BY THREADS

    StopWatchShared(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    /*
    Method which will set initial default countdown time
    This method also allows to pass StopWatch to Runnable with method reference.
     */
    public void countDown() {
        countDown(5);
    }

    /*
    unitColor is a method argument, so it is separate for each thread and stored on each thread stack
     */
    public void countDown(int unitCount) {
        // threads will be names as colors;
        String threadName = Thread.currentThread().getName();

        // initialize default thread color which will be used if the user
        // entered wrong color
        ThreadColor threadColor = ThreadColor.ANSI_RESET;
        try {
            threadColor = ThreadColor.valueOf(threadName);
        } catch (IllegalArgumentException e) {
            // User may pass a color not in the list, it will be ignored
            // as we initialized the thread color to default
        }
        String color = threadColor.color();

        // !!! variable i is not local to the thread any more
        // because in this case it is a field on the class, thus shared among threads
        // so when a thread enters the loop it starts from i which was set but another thread
        // all changes to i are visible to all threads
        for (i = unitCount; i > 0; i--) {
            // thread will sleep according to timeunit before decreasing the i
            // if the timeunit was minute, it will sleep a minute
            try {
                timeUnit.sleep(1);
            } catch (InterruptedException e) {
                // even this statement is not necessarily atomic
                // during this statement other thread could take stage
                // due to time slicing (multiple threads take turns in work on single CPU)
                e.printStackTrace();
            }
            System.out.printf("%s%s Thread : i = %d%n", color, threadName, i);
        }
    }
}
