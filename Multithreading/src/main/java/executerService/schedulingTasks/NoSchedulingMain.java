package executerService.schedulingTasks;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class NoSchedulingMain {
    /**
     * Example to show that trying to schedule tasks which return current ZonedDateTime
     * with regular FixedThreadPool
     * doesn't work as expected. We can't control exact time of execution
     * @param args
     */
    public static void main(String[] args) {
        var dtf = DateTimeFormatter.ofLocalizedDateTime(
                FormatStyle.MEDIUM,
                FormatStyle.LONG
        );

        // callable unlike runnable returns result
        Callable<ZonedDateTime> waitAndDoIt = () -> {
            ZonedDateTime zdt = null;
            try {
                TimeUnit.SECONDS.sleep(2); // simulate task running 2 seconds
                zdt = ZonedDateTime.now();
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
            return zdt;
        };

        final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(2);
        List<Callable<ZonedDateTime>> allTasks = Collections.nCopies(4, waitAndDoIt);

        try {
            System.out.println("-----> from main thread no scheduler : " + ZonedDateTime.now().format(dtf));
            final List<Future<ZonedDateTime>> futuresList = threadPoolExecutor.invokeAll(allTasks);
            for(Future<ZonedDateTime> result : futuresList) {
                try {
                    // wait a second at most to get the result from the task
                    // first will be printed after 2 sec, second after 4 etc
                    System.out.println(result.get(1,TimeUnit.SECONDS).format(dtf));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
