package test.org.app;

import test.org.http.Request;
import test.org.http.response.Response;

public abstract class Controller {

    protected final Request request;

    public Controller(Request request) {
        this.request = request;
    }

    public abstract Response doGet();
    public abstract Response doPost();
}