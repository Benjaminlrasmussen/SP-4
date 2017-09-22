package exercise1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Exercise1
{

    private static boolean run = true;
    
    public static void main(String[] args) throws InterruptedException
    {
        Thread t1 = new Thread(new SumOf1Billion());
        Thread t2 = new Thread(new Print1To5());
        Thread t3 = new Thread(new PrintFrom10AndUp());
        t1.start();
        t2.start();
        t3.start();
        
        Thread.sleep(10000);
        run = false;
    }

    private static class SumOf1Billion implements Runnable
    {

        @Override
        public void run()
        {
            long sum = 0;
            for (int i = 1; i <= 1000000000; i++)
            {
                sum += i;
            }
            System.out.println("The sum is: " + sum);
        }

    }

    private static class Print1To5 implements Runnable
    {

        @Override
        public void run()
        {
            try
            {
                System.out.println("1");
                Thread.sleep(2000);
                System.out.println("2");
                Thread.sleep(2000);
                System.out.println("3");
                Thread.sleep(2000);
                System.out.println("4");
                Thread.sleep(2000);
                System.out.println("5");
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Exercise1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    private static class PrintFrom10AndUp implements Runnable
    {

        @Override
        public void run()
        {
            int i = 10;
            while (run)
            {
                System.out.println(i);
                i++;
                try
                {
                    Thread.sleep(3000);
                }
                catch (InterruptedException ex)
                {
                    Logger.getLogger(Exercise1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
