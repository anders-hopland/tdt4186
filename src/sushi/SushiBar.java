package sushi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 15;
    private static int waitressCount = 8;
    private static int duration = 4;
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 2000; // Used to calculate the time the customer spends eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;


    public static void main(String[] args) {
        log = new File(path + "log.txt");

        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);

        Clock clock = new Clock(duration);

        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);
        Door d = new Door(waitingArea, doorWait);
        Thread doorThread = new Thread(d);
        doorThread.start();

        List<Thread> waitressThreads = new ArrayList<>();
        for (int i = 0; i < waitressCount; i++) {
            Thread t  = new Thread(new Waitress(waitingArea, i));
            t.start();
            waitressThreads.add(t);
        }

        // Termiante threads
        try {
            doorThread.join();
            for (Thread t : waitressThreads) {
                t.join();
            }
        }
        catch (InterruptedException e) {
            SushiBar.write("Error in termination of threads");
        }

        SushiBar.write("\n\nSushibar has now closed");
        SushiBar.write("Served " + customerCounter.get() + " people");
        SushiBar.write("Total numer of dishes " + totalOrders.get());
        SushiBar.write("Eaten at the restaurant " + servedOrders.get() + " dishes");
        SushiBar.write("Brought home " + takeawayOrders.get() + " number of dishes");

    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
