package ch.hearc.ig.tools;

import java.util.Map;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Request {
    private String uri;
    private String params;

    public Request(String uri, Map<String, String> params) {
        this.uri = uri;
        StringBuilder paramStr = new StringBuilder();
        for (String param : params.keySet()) {
            paramStr.append(param).append("=").append(params.get(param)).append("&");
        }
        paramStr.deleteCharAt(paramStr.length() - 1);// remove last &
        this.params = paramStr.toString();
    }

    public Request(String uri) {
        this.uri = uri;
        this.params = "";
    }

    public String call() {
        // Créer un client HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Construire une requête HTTP
        HttpRequest request;
        request = HttpRequest.newBuilder()
            .uri(URI.create(this.uri + this.params))
            .build();
        String result = "";
        try {
            //erreurs de serveur "statuscode"
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());;
            int statusCode = response.statusCode();

            if (statusCode != 200) {
                Log.warn("Response code : " + statusCode);
            }
            result = response.body();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
