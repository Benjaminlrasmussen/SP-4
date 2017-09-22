package program;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Initializer
{

    public static void main(String[] args) throws InterruptedException
    {
        System.out.println("Time with 1 producer: " + startAndTimeService(1));
        System.out.println("Time with 2 producer: " + startAndTimeService(2));
        System.out.println("Time with 3 producer: " + startAndTimeService(3));
        System.out.println("Time with 4 producer: " + startAndTimeService(4));
    }

    public static long startAndTimeService(int numberOfProducers)
    {
        long startTime = System.nanoTime();

        Random rng = new Random();
        BlockingQueue<Integer> s1 = createAndPoulateQueue();
        BlockingQueue<Long> s2 = new ArrayBlockingQueue(100);
        while (s1.remainingCapacity() > 0)
        {
            s1.add(rng.nextInt(10) + 1);
        }
        
        for (int i = 0; i < numberOfProducers; i++)
        {
            Thread p = new Thread(new FibonacciProducer(s1, s2));
            p.start();
        }

        FibonacciConsumer cons = new FibonacciConsumer(s2);
        Thread c1 = new Thread(cons);
        c1.start();

        while (!s2.isEmpty() || !FibonacciConsumer.productionStopped)
        {
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Initializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Calculation finished...");
        System.out.println("Total sum is: " + cons.getSum());

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private static BlockingQueue<Integer> createAndPoulateQueue()
    {
        BlockingQueue queue = new ArrayBlockingQueue(11);
        try
        {
            queue.put(4);
            queue.put(5);
            queue.put(8);
            queue.put(12);
            queue.put(21);
            queue.put(22);
            queue.put(34);
            queue.put(35);
            queue.put(36);
            queue.put(37);
            queue.put(42);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Initializer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return queue;
    }
}
