package executerService.warehouseChallengeWithPools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
    The shoe warehouse class:
    Should maintain a product list, as a public static field.
    It should also maintain a private list of orders.

    It should have two methods, receiveOrder and fulfillOrder.

    The receiveOrder gets called by a Producer thread.
    It should poll or loop indefinitely, checking the size of the list,
    but it should call wait if the list has reached some maximum capacity.

    The fulfillOrder gets called by a Consumer thread.
    It should also poll the list, but it needs to check if the list is empty,
    and wait in the loop, until an order is added.
    Both methods should invoke the wait and notifyAll methods appropriately.
 */

public class ShoeWarehouse {
    private List<Order> shippingItems;

    public final static String[] PRODUCT_LIST =
            {"Running Shoes", "Sandals", "Boots", "Slippers", "High Tops"};

    private final ExecutorService fulfullmentService;

    public ShoeWarehouse() {
        // linked list is also good option since it keeps insertion order and has Queue methods.
        this.shippingItems = new ArrayList<>();

        // maximum 3 threads, that fill be reused for 15 tasks.
        fulfullmentService = Executors.newFixedThreadPool(3);
    }

    public void shutDownFulfullmentService() {
        fulfullmentService.shutdown();
    }

    /**
     *   The receiveOrder gets called by a Producer thread.
     *   It should poll or loop indefinitely, checking the size of the list,
     *   but it should call wait if the list has reached some maximum capacity.
     * @param item
     */
    public synchronized void receiveOrder(Order item) {
        // there is some restriction on this list, maybe workers can't process more than x orders
        // loop through until list is full
        // do nothing while we have more than 20 orders
        // only if there is less, we may add more
        while (shippingItems.size() > 20) {
            try {
                wait();// releases this threads lock and allows
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        shippingItems.add(item);
        System.out.println(Thread.currentThread().getName() + " Incoming: " + item);
        fulfullmentService.submit(this::fulfillOrder);
        // notify all threads that there is a change that could impact them
        // i.e. that this thread has done it's job and releasing the lock.
        notifyAll();
    }

    /*
    The fulfillOrder gets called by a Consumer thread.
    It should also poll the list, but it needs to check if the list is empty,
     and wait in the loop, until an order is added.
     */
    public synchronized Order fulfillOrder() {
        // loop through until there is order to ship
        while (shippingItems.isEmpty()) {
            try {
                // release the lock to allow producer threads
                // to add some shipping items
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // once there is at least one item in the shipping list
        // we can retrieve the first item;
        Order item = shippingItems.remove(0);
        System.out.println(Thread.currentThread().getName() + " Fulfilled: " + item);
        notifyAll(); // release lock;
        return item;
    }
}
