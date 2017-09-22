package exercise4_ex1refactor;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;

public class DocumentConsumer extends Thread
{

    Thread[] producerThreads;
    BlockingQueue<Document> docQueue;

    public DocumentConsumer(Thread[] producerThreads, BlockingQueue<Document> docQueue)
    {
        this.producerThreads = producerThreads;
        this.docQueue = docQueue;
    }

    @Override
    public void run()
    {
        while (!allThreadsAreDead())
        {
            try
            {
                Thread.sleep(5);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(DocumentConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        int iterations = docQueue.size();
        for (int i = 0; i < iterations; i++)
        {
            try
            {
                Document doc = docQueue.take();
                System.out.println("Title: " + doc.title());
                System.out.println("Div's: " + doc.select("div").size());
                System.out.println("Body's: " + doc.select("body").size());
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(DocumentConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private boolean allThreadsAreDead()
    {
        for (int i = 0; i < producerThreads.length; i++)
        {
            if (producerThreads[i].isAlive())
                return false;
        }

        return true;
    }
}
