package test.org.app;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public enum QuotesService {
    INSTANCE;
    private static volatile ArrayList<Quote> quotes  = new ArrayList<>();



    public synchronized String getQuotes() {
        StringBuilder builder = new StringBuilder();
        System.out.println(quotes.size());
        for(Quote curr : quotes)
        {
            builder.append("<h3>").append(curr.toString()).append("</h3>");

        }
        return builder.toString();
    }

    public synchronized Quote getQuoteOfTheDay() {
        Random r = new Random();
        if(quotes.size() == 0)
            return new Quote("If you want to find out the secrets of the universe think in terms of energy and vibration.",
                    "Nikola Tesla");
        System.out.println(" SIZE JE OOVOLKO " +quotes.size()+"\n");
        return quotes.get( r.nextInt( 0,quotes.size() ) );
    }

    public synchronized void addQuote(String quote, String author) {
        System.out.println("DODAT QUOTE " + quote);
        Quote newQ = new Quote(quote, author);
        quotes.add(newQ);
        System.out.println(quotes.size());
    }
}
