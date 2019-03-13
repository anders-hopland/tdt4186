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
       int numEatHere = random.nextInt(5);
       for (int i = 0; i < numEatHere; i++) {
           try
           {
               Thread.sleep(200);
           }
           catch(InterruptedException ex)
           {
               Thread.currentThread().interrupt();
           }
       }
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        // TODO Implement required functionality
        return id;
    }

    // Add more methods as you see fit
}
