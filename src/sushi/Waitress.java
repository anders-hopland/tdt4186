package sushi;

import java.util.Random;

/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    private WaitingArea waitingArea;
    private Random random;
    private int id;

    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea, int id) {
        this.waitingArea = waitingArea;
         this.id = id;
         this.random = new Random();
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        SushiBar.write("Beginning");
        while (SushiBar.isOpen) {
            try
            {
                Customer customer = waitingArea.next();
                if (customer != null) {
                    //SushiBar.write("Waitress with id: " + this.id + " is waiting " + customer.getCustomerID());
                    SushiBar.write("Customer #ID " + customer.getCustomerID() + " is now fetched");

                    // Sleep
                    Thread.sleep(SushiBar.waitressWait);

                    customer.order();
                }
                else {
                    SushiBar.write("Waitress with id " + this.id + " is waiting for customers");
                    synchronized (waitingArea) {
                        waitingArea.wait();
                    }
                }
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
    }


}

