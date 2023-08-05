package test.org.http.quotes_service;

import test.org.app.Handler;
import test.org.app.QuotesRequestHandler;
import test.org.http.HttpMethod;
import test.org.http.Request;
import test.org.http.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.StringTokenizer;

class ServerThread implements Runnable {

    private final Socket client;
    private BufferedReader in;
    private PrintWriter out;

    protected ServerThread(Socket socket) {
        client = socket;
        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()));

            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    client.getOutputStream())), true);
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

            Request request = new Request(requestMethod,path);

            String  quote = null, author = null;
            int payLoadSize = 0;

            while ( !(line = in.readLine().trim()).equals("") ) {

                System.out.println(line);

                if( requestMethod == HttpMethod.POST && line.contains("Content-Length") ) {
                    try{
                        payLoadSize = Integer.parseInt(line.split(":")[1].trim());
                        System.out.println(payLoadSize+"\n");
                    }catch (NumberFormatException e) {
                        throw new RuntimeException("Couldn't get payload size in post request!", e);
                    }
                }
            }

            if(payLoadSize > 0)
            {

                char[] buffer = new char[payLoadSize];
                in.read(buffer);

                String payLoad = String.valueOf(buffer);
                System.out.println(payLoad);

                if (payLoad.contains("quote") && payLoad.contains("author"))
                {
                    String[] split = payLoad.split("&");
                    quote = split[0].split("=")[1].trim().replace("+"," ");
                    author = split[1].split("=")[1].trim().replace("+"," ");
                }

                request.getPayload().put("Quote", quote);
                request.getPayload().put("Author", author);

            }

            Handler handler = new QuotesRequestHandler();
            Response response = handler.handle(request);

            out.println("\n\n" + response.getResponse());

            in.close();
            out.close();
            client.close();
        }


        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
