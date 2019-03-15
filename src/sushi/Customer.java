package sushi;

import java.util.Random;

/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */

    private final int id;

    public Customer(int id) {
        this.id = id;
    }

   /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order(){
       Random random = new Random();
       try
       {
           Thread.sleep(SushiBar.customerWait); // Wait

           int numOrders = Math.abs(random.nextInt(SushiBar.maxOrder));

           // random bound must be greater than 0
           int bringHomeOrders;
           if (numOrders > 0) bringHomeOrders = random.nextInt(numOrders);
           else bringHomeOrders = 0;

           SushiBar.customerCounter.add(1);
           SushiBar.takeawayOrders.add(bringHomeOrders);
           SushiBar.servedOrders.add(numOrders - bringHomeOrders);
           SushiBar.totalOrders.add(numOrders);

           // Log events
           SushiBar.write(
                    "Customer is: " + this.id +
                        ", total orders: " + numOrders +
                        ", took home " + bringHomeOrders
            );
       }
       catch(InterruptedException ex)
       {
           Thread.currentThread().interrupt();
       }
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        return id;
    }
}
