package noExecuter.sharedMemoryStopwatch3;


import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/*
In this program no objects are shared (each thread is separate)
 */
public class ThreadColorNotSharedMain {
    public static void main(String[] args) {

        // This instance of stopwatch is shared among all threads,
        // but it is not  a problem, because in the countDown method itself
        // nothing is shared (unitCount comes from outside and is local to each thread)
        StopWatchNotShared stopWatch = new StopWatchNotShared(TimeUnit.SECONDS);

        Thread green = new Thread(stopWatch::countDown, ThreadColor.ANSI_GREEN.name());
        Thread purple = new Thread(() -> stopWatch.countDown(7), ThreadColor.ANSI_PURPLE.name());
        Thread red = new Thread(stopWatch::countDown, ThreadColor.ANSI_RED.name());

        green.start();
        purple.start();
        red.start();
    }
}

/*
Stop watch counts time in the timeUnit specified (like minutes, seconds etc)
 */
class StopWatchNotShared {
    private TimeUnit timeUnit;

    StopWatchNotShared(TimeUnit timeUnit) {
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
        for (int i = unitCount; i > 0; i--) {
            // thread will sleep according to timeunit before decreasing the i
            // if the timeunit was minute, it will sleep a minute
            try {
                timeUnit.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s%s Thread : i = %d%n", color, threadName, i);
        }
    }
}
