package test.org.app;

public class Quote {
    private String quote;
    private String author;
    public  Quote(){

    }
    public Quote (String quote, String author) {
        this.author = author;
        this.quote = quote;
    }

    @Override
    public String toString() {
        return quote + "\r\n by " + author ;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public String getQuote() {
        return quote;
    }
}