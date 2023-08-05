package test.org.http.response;

public class RedirectResponse implements Response{

    private final String location;

    public RedirectResponse(String location) {
        this.location = location;
    }


    @Override
    public String getResponse() {
        return "HTTP/1.1 301 OK\r\nLocation: " + this.location + "\r\n\r\n";
    }
}
