package org.example.lockChallengeWarehouse7;

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

record Order(long orderId, String item, int qty){

}

public class ShoeWarehouseMain {

    private static final Random random = new Random();
    public static void main(String[] args) {
        // the warehouse is the shared resource;
        // specifically both threads try to manipulate the shippingOrders list;
        ShoeWarehouse warehouse = new ShoeWarehouse();

        Thread producerThread = new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                warehouse.receiveOrder(new Order(
                        random.nextLong(1000000, 9999999), // 7 digit number
                        ShoeWarehouse.PRODUCT_LIST[random.nextInt(0, 5)], // pick random shoe from exsting list of 5 types
                        random.nextInt(1, 4) // random qty from 1 to 3
                ));
            }
        });
        producerThread.start();

        // create 2 consumer threads (maybe more threads)
        for (int i = 0; i < 2; i++) {
            Thread consumerThread = new Thread(()->{
                for (int j = 0; j < 5; j++) {
                    Order fulfilledOrder = warehouse.fulfillOrder();
                }
            });
            consumerThread.start();
        }
    }
}
