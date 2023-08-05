package test.org.http;

import test.org.http.HttpMethod;

import java.util.HashMap;

public class Request {
    private final HttpMethod httpMethod;
    private final String path;

    private HashMap<String,String> payload;

    public Request(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
        if(httpMethod == HttpMethod.POST)
            payload = new HashMap<>();
    }

    @Override
    public String toString() {
        return httpMethod + " " + path + " HTTP/1.1\r\n\r\n "
                + ( (httpMethod == HttpMethod.POST) ? "quote=" + payload.get("Quote") + "&author=" + payload.get("Author") : "");
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public HashMap<String, String> getPayload() throws RuntimeException {
        if(!(httpMethod == HttpMethod.POST))
            throw new RuntimeException("This is not POST request, it doesnt have a payload.");
        return payload;
    }

}
