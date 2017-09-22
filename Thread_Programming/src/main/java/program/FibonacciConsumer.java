package program;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FibonacciConsumer implements Runnable
{
    public static boolean productionStopped = false;
    
    private BlockingQueue<Long> fibValues;
    private long sum;

    public FibonacciConsumer(BlockingQueue fibValues)
    {
        this.fibValues = fibValues;
    }

    @Override
    public void run()
    {
        while (!fibValues.isEmpty() || !productionStopped)
        {
            try
            {
                Long taken = fibValues.poll(1, TimeUnit.MILLISECONDS);
                
                if (taken != null)
                {
                    System.out.println("Value: " + taken);
                    sum += taken;
                }
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(FibonacciConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public long getSum()
    {
        return sum;
    }
}
