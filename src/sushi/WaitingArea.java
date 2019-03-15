package sushi;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {

    /**
     * Creates a new waiting area.
     *
     * @param size The maximum number of Customers that can be waiting.
     */

    private int size;
    private Queue<Customer> queue;

    public WaitingArea(int size) {
        this.size = size;
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized boolean enter(Customer customer) {
        SushiBar.write("Customer entered");
        int qLength = 0;
        for (Customer c : this.queue) {
            qLength++;
        }

        if (qLength < this.size) {
            this.queue.add(customer);
            return true;
        }
        else return false;
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        if (!this.queue.isEmpty()) return this.queue.poll();
        else return null;
    }

    // Add more methods as you see fit
}
