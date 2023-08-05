package test.org.http.response;

public class HtmlResponse implements Response {
    private final String html;

    public HtmlResponse(String response) {
        html = response;
    }

    @Override
    public String getResponse() {
        return "HTTP/1.1 200 OK \r\nContent-Type: text/html\r\n\r\n" + html;
    }
}
