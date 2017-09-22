package exercise1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tester {

    public static void main(String[] args)
    {
        System.out.println("Avilable Processors: " + Runtime.getRuntime().availableProcessors());

        TagCounter tc1 = new TagCounter("http://www.fck.dk");
        TagCounter tc2 = new TagCounter("http://www.google.com");
        TagCounter tc3 = new TagCounter("http://politiken.dk/");

        long start = System.nanoTime();
        tc1.run();
        tc2.run();
        tc3.run();
        long end = System.nanoTime();
        System.out.println("Time Sequential: " + (end - start));

        long start2 = System.nanoTime();
        tc1.start();
        tc2.start();
        tc3.start();
        while (tc1.isAlive() || tc2.isAlive() || tc3.isAlive())
        {
            try
            {
                Thread.sleep(0, 1);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        long end2 = System.nanoTime();
        System.out.println("Time Parallel: " + (end2 - start2));
    }
}
