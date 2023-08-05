package test.org.app;

import com.google.gson.Gson;
import test.org.http.HttpMethod;
import test.org.http.Request;
import test.org.http.response.HtmlResponse;
import test.org.http.response.JsonResponse;
import test.org.http.response.RedirectResponse;
import test.org.http.response.Response;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Iterator;

import static test.org.http.save_quote_service.QuotesServer.TCP_PORT_2;

public class QuotesController extends Controller{

    public QuotesController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        String content = "<html><head><title>GET \\quotes</title></head>\r\n";

        String htmlBody =
                "<h1>save quote \r\n\r\n</h1>" +
                "<form method=\"POST\" action =\"/save-quote\">" +
                "<label for=\"quote\">Quote:</label>" +
                "<input type=\"text\" id=\"quote\" name=\"quote\"><br><br>" +
                "<label for=\"author\">Author:</label>" +
                "<input type=\"text\" id=\"author\" name=\"author\"><br><br>" +
                "<button type=\"submit\">Save Quote</button>" +
                "</form>";

        try
        {
            String quoteOfTheDay = sendRequest();
            htmlBody += "<label> Quote of the day: </label>" +
                        "<h2>" + quoteOfTheDay + "</h2>";
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        htmlBody += "<label>Your quotes:</label>"+ QuotesService.INSTANCE.getQuotes();

        content += "<body>" + htmlBody + "</body></html>";
        System.out.println(content);
        return new HtmlResponse(content);
    }

    @Override
    public Response doPost() {
        try {
            String feedBack = sendRequest();
            if(!feedBack.equals("HTTP/1.1 200 OK"))
                throw new RuntimeException();
            QuotesService.INSTANCE.addQuote(request.getPayload().get("Quote"), request.getPayload().get("Author"));
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectResponse("/quotes");
    }

    private String sendRequest() throws IOException {

        Socket socket = new Socket("127.0.0.1", TCP_PORT_2);
        System.out.println("\nSERVER 1 NAPRAVIO SOKET DA PRICA SA SERVEROM 2\n");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

        if (request.getHttpMethod() == HttpMethod.POST) {

            String json = new Gson().toJson(new Quote(request.getPayload().get("Quote"), request.getPayload().get("Author")));

            out.println(request.getHttpMethod() + " " + request.getPath() + " HTTP/1.1\r\n"
                    + "Content-Length: " + json.length() + "\r\n\r\n" + json);
            System.out.println("\nSERVER 1 POSLAO POST ZAHTEV NA SERVER 2 SA JSONOM:\n" + json + "\n");

            String line = in.readLine();
            System.out.println("\nSERVER 1 DOCEKAO ODGOVOR OD SERVERA 2 \n");


            if (line.equals("HTTP/1.1 200 OK")) {
                in.close();
                out.close();
                socket.close();
                System.out.println("\nKOMUNIKACIJA ZAVRSENA OK\n");
                return line;
            }
            else throw new RemoteException("Nije uspela komunikacija sa save-quote serverom.");
        }

        else
        {
            System.out.println("\nSERVER 1 USAO U GRANU\n");
            out.println(request.getHttpMethod() + " " + request.getPath() + " HTTP/1.1\r\n");

            String line;
            while (!(line = in.readLine()).trim().equals("")) {
                System.out.println(line);
            }
            Quote quote = new Gson().fromJson(in.readLine(), Quote.class);
            System.out.println("\nSERVER 1 DOBIO QUOTE DANA OD SERVERA 2\n");

            return quote.toString();
        }
    }
}
