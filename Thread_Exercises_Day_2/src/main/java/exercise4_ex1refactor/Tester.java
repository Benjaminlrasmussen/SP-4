package exercise4_ex1refactor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import org.jsoup.nodes.Document;

public class Tester {
    
    public static void main(String[] args)
    {
        BlockingQueue<String> urlQueue = new ArrayBlockingQueue(3);
        BlockingQueue<Document> docQueue = new ArrayBlockingQueue(3);
        Thread p1 = new DocumentProducer(urlQueue, docQueue);
        Thread p2 = new DocumentProducer(urlQueue, docQueue);
        Thread p3 = new DocumentProducer(urlQueue, docQueue);
        Thread p4 = new DocumentProducer(urlQueue, docQueue);
        urlQueue.add("http://www.fck.dk");
        urlQueue.add("http://www.google.com");
        urlQueue.add("http://politiken.dk/");
        
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        
        Thread[] threads = {p1, p2, p3, p4};
        Thread c1 = new DocumentConsumer(threads, docQueue);
        c1.start();
    }
}
