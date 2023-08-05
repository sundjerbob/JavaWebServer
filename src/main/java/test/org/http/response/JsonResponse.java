package test.org.http.response;

public class JsonResponse implements Response{

    private String json;

    public JsonResponse(String json) {
        this.json = json;
    }
    @Override
    public String getResponse() {
        return "HTTP/1.1 200 OK \r\nContent-Type: application/json\r\nContent-Length: " + json.length() + "\r\n\r\n" + json;
    }
}
