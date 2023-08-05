package test.org.app;

import com.google.gson.Gson;
import test.org.http.Request;
import test.org.http.response.HtmlResponse;
import test.org.http.response.JsonResponse;
import test.org.http.response.Response;

public class SaveQuoteController extends Controller{
    public SaveQuoteController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {

        return new JsonResponse(new Gson().toJson(QuotesService.INSTANCE.getQuoteOfTheDay()));
    }

    @Override
    public Response doPost() {
        QuotesService.INSTANCE.addQuote(request.getPayload().get("Quote"),request.getPayload().get("Author"));

        return () -> "HTTP/1.1 200 OK\r\n\r\n";

    }
}
