package test.org.app;

import test.org.http.HttpMethod;
import test.org.http.Request;
import test.org.http.response.Response;

public class SaveQuoteRequestHandler implements Handler{

    @Override
    public Response handle(Request request) throws Exception {

        if (request.getPath().equals("/quotes") && request.getHttpMethod() == HttpMethod.GET) {
            System.out.println("\n\n------------------------------------\nSERVER 2 CALLING DO_GET\n-------------------------------------\n\n");
            return new SaveQuoteController(request).doGet();
        }

        else if (request.getPath().equals("/save-quote") && request.getHttpMethod().equals(HttpMethod.POST)){
            System.out.println("\n\n------------------------------------\nSERVER 2 CALLING DO_POST\n-------------------------------------\n\n");
            return new SaveQuoteController(request).doPost();
        }



        throw new Exception("Page: " + request.getPath() + ". Method: " + request.getHttpMethod() + " not found!");

    }

}
