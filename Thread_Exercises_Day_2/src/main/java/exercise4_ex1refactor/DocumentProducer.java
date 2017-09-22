package exercise4_ex1refactor;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentProducer extends Thread {

    BlockingQueue<String> urlQueue;
    BlockingQueue<Document> docQueue;
    
    DocumentProducer(BlockingQueue urlQueue, BlockingQueue docQueue)
    {
        this.urlQueue = urlQueue;
        this.docQueue = docQueue;
    }

    /*
  Connect to the URL and count the number of h1, h2, div and body Tags
     */
    public void run()
    {
        Document doc;
        try
        {
            String url = urlQueue.poll(5, TimeUnit.SECONDS);
            doc = Jsoup.connect(url).get();
            docQueue.put(doc);
            
        } catch (IOException | InterruptedException | IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }

}
