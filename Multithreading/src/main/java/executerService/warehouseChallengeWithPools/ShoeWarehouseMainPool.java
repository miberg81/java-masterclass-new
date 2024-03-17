package executerService.warehouseChallengeWithPools;

/*
In this challenge, you'll be creating your own Producer Consumer example,
 for a Shoe Warehouse Fulfillment Center.
The producer code should generate orders,
and send them to the Shoe Warehouse to be processed.
The consumer code should fulfill, or process the orders
 in a FIFO or first in,first out order.
You'll be creating at a minimum, three types for this,
 an Order, a Shoe Warehouse, and a Main executable class.
 */

/*
Finally, you'll need some kind of a Main class with a main method, to execute.
This method should create and start a single Producer thread.
 This should generate 10 sales orders,
 and call receiveOrder on the Shoe Warehouse, for each.
In addition, you'll create and start two Consumer threads.
 Each thread needs to process 5 fulfillment orders,
 calling fulfillOrder on the Shoe Warehouse for each item.
You'll test your Producer Consumer application,
and confirm your application fulfills all the 10 orders it receives
 */

import java.util.Random;
import java.util.concurrent.*;

record Order(long orderId, String item, int qty){

}

public class ShoeWarehouseMainPool {

    private static final Random random = new Random();
    private static int ordersAmount = 10;

    public static void main(String[] args) {
        // the warehouse is the shared resource;
        // specifically both threads try to manipulate the shippingOrders list;
        ShoeWarehouse warehouse = new ShoeWarehouse();

        ExecutorService orderingService = Executors.newCachedThreadPool();
        // Submitting orders code should be fast
        // We should allow submitting as many orders as possible as fast as possible.
        // There is no for loop for the task because we can use executorService to fire multiple tasks.
        Callable<Order> orderingTask = () -> {
            Order newOrder = getOrder();
            try {
                Thread.sleep(random.nextInt(500, 5000));
                warehouse.receiveOrder(newOrder);
            } catch (InterruptedException e) {
                throw  new RuntimeException(e);
            }
            return newOrder;
        };

        // setup array of tasks
//        List<Callable<Order>> tasks = Collections.nCopies(15, orderingTask);
//        try {
//            orderingService.invokeAll(tasks);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        orderingService.shutdown();
        try {
            // when 15 tasks are fired, cached pool of threads increases to 15 threads
            for (int j = 0; j < 15; j++) {
                // because of delays between submitted orders,
                // tasks are executed faster then the new incoming orders are created
                // => threads within pool are reused many times
                Thread.sleep(random.nextInt(5, 10));
                orderingService.submit(() -> warehouse.receiveOrder(getOrder()));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            // wait for all task to finish, but no longer than 6 seconds!
            orderingService.awaitTermination(6, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        warehouse.shutDownFulfullmentService();

        // create 2 consumer threads (maybe more threads)
        try (var consumerMultiExecutor = Executors.newCachedThreadPool()) {
            for (int i = 0; i < ordersAmount; i++) {
                final Future<Order> submit = consumerMultiExecutor.submit(() -> warehouse.fulfillOrder());
            }
        }
    }

    private static Order getOrder() {
        return new Order(
                random.nextLong(1000000, 9999999), // 7 digit number
                ShoeWarehouse.PRODUCT_LIST[random.nextInt(0, 5)], // pick random shoe from exsting list of 5 types
                random.nextInt(1, 4));// random qty from 1 to 3
    }
}
