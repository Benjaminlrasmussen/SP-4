package exercise2;

public class Exercise2
{

    public static void main(String[] args)
    {
        Even even = new Even();
        Thread t1 = new Thread(new CallEven("Thread1", even, 5));
        Thread t2 = new Thread(new CallEven("Thread2", even, 5));

        t1.start();
        t2.start();
    }

    private static class Even
    {

        private int n = 0;

        public int next()
        {
            n++;
            n++;
            return n;
        }
    }

    private static class CallEven implements Runnable
    {

        private final String threadName;
        private final Even evenObj;
        private final int iterations;

        public CallEven(String threadName, Even evenObj, int iterations)
        {
            this.threadName = threadName;
            this.evenObj = evenObj;
            this.iterations = iterations;
        }

        @Override
        public void run()
        {
            synchronized (evenObj)
            {
                for (int i = 0; i < iterations; i++)
                {
                    System.out.println(threadName + " prints: " + evenObj.next());
                }
            }
        }

    }
}
