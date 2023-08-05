package test.org.http.save_quote_service;

import com.google.gson.Gson;
import test.org.app.Handler;
import test.org.app.Quote;
import test.org.app.QuotesRequestHandler;
import test.org.app.SaveQuoteRequestHandler;
import test.org.http.HttpMethod;
import test.org.http.Request;
import test.org.http.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;

class QuotesServerThread implements Runnable {
    private final Socket client;
    private BufferedReader in;
    private PrintWriter out;

    protected QuotesServerThread(Socket socket) {
        client = socket;
        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()));

            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(client.getOutputStream())), true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {


        try {
            String line = in.readLine();
            System.out.println(line);

            StringTokenizer tokenizer = new StringTokenizer(line);
            HttpMethod requestMethod = HttpMethod.valueOf(tokenizer.nextToken());
            String path = tokenizer.nextToken();

            Request request = new Request(requestMethod, path);

            while ( !(line = in.readLine().trim()).equals("") ) {
                System.out.println(line);

            }
            String json;
            if ( requestMethod == HttpMethod.POST )
            {
                json = in.readLine();
                System.out.println("SERVER 2 GOT POST REQUEST WITH JSON: " + json);
                Quote quote = new Gson().fromJson(json, Quote.class);
                request.getPayload().put("Quote", quote.getQuote());
                request.getPayload().put("Author", quote.getAuthor());
            }

            Handler handler = new SaveQuoteRequestHandler();
            Response response = handler.handle(request);
            out.println(response.getResponse());

            in.close();
            out.close();
            client.close();

            }
        catch (Exception e) {
            e.printStackTrace();
        }



    }





}

