package executerService.threadPoolsExecutor;

import executerService.noExecuter.ThreadColor;

import java.util.List;
import java.util.concurrent.*;


public class CachedPoolExecutorMain {

    /**
     * Here we pass a list of tasks and get a list of results
     * @param args
     */
    public static void mainInvokeAny(String[] args) {

        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> tasksList = List.of(
                () -> CachedPoolExecutorMain.sum(1,10,1,"red"),
                () -> CachedPoolExecutorMain.sum(1,100,10,"blue"),
                () -> CachedPoolExecutorMain.sum(2,20,2,"green")
        );
        try {
            var result = multiExecutor.invokeAny(tasksList);
            System.out.println(result);
        } catch (InterruptedException  | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            // thread pool stays alive until somebody stops it
            multiExecutor.shutdown();
        }
    }

    /**
     * Here we pass a list of tasks and get a list of results
     * @param args
     */
    public static void mainInvokeAll(String[] args) {

        var multiExecutor = Executors.newCachedThreadPool();
        List<Callable<Integer>> tasksList = List.of(
                () -> CachedPoolExecutorMain.sum(1,10,1,"red"),
                () -> CachedPoolExecutorMain.sum(1,100,10,"blue"),
                () -> CachedPoolExecutorMain.sum(2,20,2,"green")
        );
        try {
            var results = multiExecutor.invokeAll(tasksList);
            for (var result : results) {
                System.out.println(result.get(500, TimeUnit.MILLISECONDS));
            }
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            // thread pool stays alive until somebody stops it
            multiExecutor.shutdown();
        }
    }

    /*
        Threads amount is not specified here.
        The masks we add, the more threads are fired up.
        This will create threads and cache them for 60 seconds(keepAliveTime)
        After this time it will drop those threads
        The submit method, unlike execute, can run also a Callable
        (which unlike Runnable does return a value)
     */
    public static void cachedMainManualResults(String[] args) {

        var multiExecutor = Executors.newCachedThreadPool();
       try {
           var redValue = multiExecutor.submit(
                   ()->CachedPoolExecutorMain.sum(1,10,1,"red"));
           var blueValue = multiExecutor.submit(
                   ()->CachedPoolExecutorMain.sum(10,100,10,"blue"));
           var greenValue = multiExecutor.submit(
                   ()->CachedPoolExecutorMain.sum(2,20,2,"green"));

           try {
               // get results (wait 500ms - will block to wait)
               System.out.println(redValue.get(500, TimeUnit.SECONDS));
               System.out.println(blueValue.get(500, TimeUnit.SECONDS));
               System.out.println(greenValue.get(500, TimeUnit.SECONDS));
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       } finally {
           // thread pool stays alive until somebody stops it
           multiExecutor.shutdown();
       }
    }

    private static int sum(int start, int end, int delta, String colorString) {
        var threadColor = ThreadColor.ANSI_RESET; // default if user did not set...
        try {
            threadColor = ThreadColor.valueOf("ANSI_"
                    + colorString.toUpperCase());
        } catch (IllegalArgumentException ignore) {

        }
        String color = threadColor.color();

        int sum = 0;
        for (int i = start; i <= end; i+=delta) {
            sum+=i;
        }
        System.out.println(color + Thread.currentThread().getName() + ", "
                + colorString + " " + sum);
        return  sum;
    }
}


