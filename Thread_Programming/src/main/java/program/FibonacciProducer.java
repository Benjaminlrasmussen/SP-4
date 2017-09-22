package program;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FibonacciProducer implements Runnable
{

    private BlockingQueue<Integer> values;
    private BlockingQueue<Long> fibonacciValues;

    public FibonacciProducer(BlockingQueue<Integer> values, BlockingQueue<Long> fibonacciValues)
    {
        this.values = values;
        this.fibonacciValues = fibonacciValues;
    }

    @Override
    public void run()
    {
        while (!values.isEmpty())
        {
            Integer taken = null;
            try
            {
                taken = values.poll(1, TimeUnit.MILLISECONDS);

                if (taken != null)
                {
                    long fib = fibonacci(taken);
                    fibonacciValues.put(fib);
                }
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(FibonacciProducer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        FibonacciConsumer.productionStopped = true;
    }

    private long fibonacci(int n)
    {
        if (n == 0 || n == 1)
            return n;

        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
