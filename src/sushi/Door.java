package sushi;

import java.util.Random;

/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 */
public class Door implements Runnable {

    public WaitingArea waitingArea;
    private int nextCustomerId;
    private Random random;
    private int waitInterval;

    /**
     * Creates a new Door. Make sure to save the
     * @param waitingArea   The customer queue waiting for a seat
     */
    public Door(WaitingArea waitingArea, int waitInterval) {
        this.waitingArea = waitingArea;
        this.nextCustomerId = 0;
        this.random = new Random();
        this.waitInterval = waitInterval;
    }

    /**
     * This method will run when the door thread is created (and started)
     * The method should create customers at random intervals and try to put them in the waiting area
     */
    @Override
    public void run() {
        while (SushiBar.isOpen) {
            try
            {
                Thread.sleep(this.waitInterval);
                if (this.random.nextInt(77) > 48) {
                    if (this.waitingArea.enter(new Customer(nextCustomerId))) {
                        this.nextCustomerId++;
                    }
                    else {
                        SushiBar.write("Waiting area is full");
                    }

                }
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }

        SushiBar.write("Closing door");
        synchronized (waitingArea) {
            waitingArea.notifyAll();
        }
    }

}
