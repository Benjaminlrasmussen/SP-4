package exercise4;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//This is the class template for the four Producer Threads P1-P4 in the exercise figure
public class DocumentProducer implements Runnable
{

    BlockingQueue<String> urlsToUse;
    BlockingQueue<Document> producedDocuments;
    Document doc;

    public DocumentProducer(BlockingQueue<String> urlsToUse, BlockingQueue producedDocuments)
    {
        this.urlsToUse = urlsToUse;
        this.producedDocuments = producedDocuments;
    }

    @Override
    public void run()
    {
        boolean moreUrlsToFetch = true;
        while (moreUrlsToFetch)
        {
            try
            {
                String url = urlsToUse.poll(2, TimeUnit.SECONDS);

                if (url == null)
                {
                    moreUrlsToFetch = false;
                    DocumentConsumer.moreDocumentsBeingProduced = false;
                }
                else
                {

                    doc = Jsoup.connect(url).get();
                    producedDocuments.put(doc);

                }
            }
            catch (IOException | InterruptedException ex)
            {
                Logger.getLogger(DocumentProducer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
