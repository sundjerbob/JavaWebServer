package test.org.app;

import test.org.http.Request;
import test.org.http.response.Response;

public interface Handler {

    public Response handle(Request request) throws Exception;
}
