package ex20;

/*
 * Code taken from 
 * http://crunchify.com/how-to-get-ping-status-of-any-http-end-point-in-java/
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SequentialPinger
{

    private static List<Future<String>> futures = new ArrayList();

    public static void main(String args[]) throws Exception
    {

        String[] hostList =
        {
            "http://crunchify.com", "http://yahoo.com",
            "http://www.ebay.com", "http://google.com",
            "http://www.example.co", "https://paypal.com",
            "http://bing.com/", "http://techcrunch.com/",
            "http://mashable.com/", "http://thenextweb.com/",
            "http://wordpress.com/", "http://cphbusiness.dk/",
            "http://example.com/", "http://sjsu.edu/",
            "http://ebay.co.uk/", "http://google.co.uk/",
            "http://www.wikipedia.org/",
            "http://dr.dk", "http://pol.dk", "https://www.google.dk",
            "http://phoronix.com", "http://www.webupd8.org/",
            "https://studypoint-plaul.rhcloud.com/", "http://stackoverflow.com",
            "http://docs.oracle.com", "https://fronter.com",
            "http://imgur.com/", "http://www.imagemagick.org"
        };

        ExecutorService es = Executors.newFixedThreadPool(10);
        
        long startTime = System.nanoTime();
        for (int i = 0; i < hostList.length; i++)
        {
            String url = hostList[i];
            futures.add(es.submit(new PingerRunnable(url)));
        }

        for (int i = 0; i < futures.size(); i++)
        {
            if (!futures.get(i).isDone())
            {
                Thread.sleep(1);
                i--;
            }
        }
        
        for (int i = 0; i < futures.size(); i++)
        {
            System.out.println(futures.get(i).get());
        }
        long endTime = System.nanoTime();
        System.out.println("Time elapsed parallel: " + (endTime - startTime));

        es.shutdown();
        es.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Closing Down");

        // Time elapsed with sequential: 11449308461 ns
        // Time elapsed with parallel:   3630090383  ns
        
        /*
        Performance gain is significant and would be of even greater value
        compared to the effort of using an executor, if the list of urls is
        longer.
        */
        
        
        startTime = System.nanoTime();
        for (int i = 0; i < hostList.length; i++) {
 
            String url = hostList[i];
            String status = getStatus(url);
 
            System.out.println(url + "\t\tStatus:" + status);
        }
        endTime = System.nanoTime();
        System.out.println("Time elapsed sequential: " + (endTime - startTime));
        
    }

    public static String getStatus(String url) throws IOException
    {

        String result = "Error";
        try
        {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            if (code == 200)
                result = "Green";
            if (code == 301)
                result = "Redirect";
        }
        catch (Exception e)
        {
            result = "->Red<-";
        }
        return result;
    }

    private static class PingerRunnable implements Callable<String>
    {

        String url;

        public PingerRunnable(String url)
        {
            this.url = url;
        }

        @Override
        public String call() throws Exception
        {
            String status = "Error";
            try
            {
                status = getStatus(url);
            }
            catch (IOException ex)
            {
                Logger.getLogger(SequentialPinger.class.getName()).log(Level.SEVERE, null, ex);
            }

            return url + "\t\tStatus:" + status;
        }
    }
}
