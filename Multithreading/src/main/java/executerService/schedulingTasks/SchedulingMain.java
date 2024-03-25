package executerService.schedulingTasks;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulingMain {
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
//
//        System.out.println("----> intervals, no initial delay: " + ZonedDateTime.now().format(dtf));
//        ScheduledExecutorService scheduledExecutor1 = Executors.newScheduledThreadPool(4);
//        for (int i=0; i<4; i++) {
//            scheduledExecutor1.schedule(
//                    () -> System.out.println(ZonedDateTime.now().format(dtf)),
//                    2 * (i+1), // print time every 2 seconds
//                    TimeUnit.SECONDS
//            );
//        }
//        scheduledExecutor1.shutdown();


        // because of initial delay, nothing happens for first 2 secinds
        // => nothing printed out because executor is shutdown
        // even before the first task is kicked off
//        ScheduledExecutorService scheduledExecutor2= Executors.newScheduledThreadPool(4);
//        System.out.println("----> intervals, with initial delay: " + ZonedDateTime.now().format(dtf));
//        scheduledExecutor2.scheduleWithFixedDelay(
//                () -> System.out.println(ZonedDateTime.now().format(dtf)),
//                2,
//                2, // print time every 2 seconds
//                TimeUnit.SECONDS
//        );
//        scheduledExecutor2.shutdown();


        /*
        Print out time every 2 seconds for 10 seconds then stop.
        Tasks themselves don't have delay, so about 5-6 prints expected within 10 seconds.
        If the printing itself is long, delays will be more than 2 seconds!
        scheduleWithFixedDelay schedules the next task after the fist finishes
         */
//        ScheduledExecutorService scheduledExecutor3= Executors.newScheduledThreadPool(4);
//        System.out.println("----> intervals, with initial delay: " + ZonedDateTime.now().format(dtf));
//        var scheduledTask = scheduledExecutor3.scheduleWithFixedDelay(
//                () -> System.out.println(ZonedDateTime.now().format(dtf)),
//                2,
//                2, // print time every 2 seconds
//                TimeUnit.SECONDS
//        );
//        long initialTime = System.currentTimeMillis();
//        // cancel any unfinished task after 10 seconds
//        while(!scheduledTask.isDone()) {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            long elapsedTime = System.currentTimeMillis() - initialTime;
//            if (elapsedTime / 1000 > 10) {
//                scheduledTask.cancel(true);
//            }
//        }
//        // shutdown executor after some time elapsed allowing for tasks to finish
//        scheduledExecutor3.shutdown();

         /*
        Here the task itself takes 3 seconds, then the delay betwean task
        from the scheduler is 2 sec, so total time for task is 5 sec.
        So within 10 seconds there will be 2 prints only
         */
//        ScheduledExecutorService scheduledExecutor4= Executors.newScheduledThreadPool(4);
//        System.out.println("----> intervals, with initial delay: " + ZonedDateTime.now().format(dtf));
//
//        final Runnable scheduledTaskWithDelay = () -> {
//            try {
//                TimeUnit.SECONDS.sleep(3);
//                System.out.println(ZonedDateTime.now().format(dtf));
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        };
//
//        var scheduledTask = scheduledExecutor4.scheduleWithFixedDelay(
//                scheduledTaskWithDelay,
//                2,
//                2, // print time every 2 seconds
//                TimeUnit.SECONDS
//        );
//
//        long initialTime = System.currentTimeMillis();
//        // cancel any unfinished task after 10 seconds
//        while(!scheduledTask.isDone()) {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            long elapsedTime = System.currentTimeMillis() - initialTime;
//            if (elapsedTime / 1000 > 10) {
//                scheduledTask.cancel(true);
//            }
//        }
//        // shutdown executor after some time elapsed allowing for tasks to finish
//        scheduledExecutor4.shutdown();


        /**
         * ScheduleAtFixedRate example:
         * Run with fixed intervals, regardless how much the task itself takes
         * Task B is short so executes every 2 seconds as expected
         */
        ScheduledExecutorService scheduledExecutor5= Executors.newScheduledThreadPool(4);
        System.out.println("----> intervals, with initial delay: " + ZonedDateTime.now().format(dtf));

        final Runnable longTask = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("A - long task " + ZonedDateTime.now().format(dtf));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        /**
         * Here the tasks will print after 3 seconds, because it is longer then the delay for next task
         * which is only 2 seconds, So tasks will not finish within 2 seconds as expected in the delay
         * So this code will print each 3 seconds, same as task duration
         * With FixedDelay next task maybe scheduled before the previous is completed, so
         * tasks are queued up.
         */
        var scheduledTask = scheduledExecutor5.scheduleWithFixedDelay(
                longTask,
                2,
                2, // print time every 2 seconds
                TimeUnit.SECONDS
        );

        var scheduledTask2 = scheduledExecutor5.scheduleWithFixedDelay(
                () -> System.out.println("B - short task " + ZonedDateTime.now().format(dtf)),
                2,
                2, // print time every 2 seconds
                TimeUnit.SECONDS
        );

        long initialTime = System.currentTimeMillis();
        // cancel any unfinished task after 10 seconds
        while(!scheduledTask.isDone()) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long elapsedTime = System.currentTimeMillis() - initialTime;
            if (elapsedTime / 1000 > 10) {
                scheduledTask.cancel(true);
            }
        }
        // shutdown executor after some time elapsed allowing for tasks to finish
        scheduledExecutor5.shutdown();

    }
}
