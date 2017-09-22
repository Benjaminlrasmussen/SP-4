package exercise4;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DocumentConsumer implements Runnable
{
    public static boolean moreDocumentsBeingProduced = true;
            
    BlockingQueue<Document> producedDocuments;
    Document doc;

    public DocumentConsumer(BlockingQueue<Document> producedDocuments)
    {
        this.producedDocuments = producedDocuments;
    }

    @Override
    public void run()
    {
        int totalDivs = 0;
        while (!producedDocuments.isEmpty() || moreDocumentsBeingProduced)
        {
            try
            {
                doc = producedDocuments.poll(1, TimeUnit.SECONDS);
                if (doc != null)
                {
                    String title = doc.title();
                    Elements divs = doc.select("div");
                    totalDivs += divs.size();

                    System.out.println("Title: " + title);
                    System.out.println("DivCount: " + divs.size());
                }

            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(DocumentConsumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Sum of all Divs:" + totalDivs);
    }

}

//TODO Update the totalDivs variable and print title and sum for this document