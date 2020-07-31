package graph.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientWrapper {

  private static HttpClient client = HttpClient.newBuilder().build();

  public static HttpResponse httpCall (HttpMethodsEnum httpMethod, String url, String payload) {
    HttpRequest.Builder requestBuilder;
    requestBuilder = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Content-Type", "application/json");

    if (httpMethod.equals(HttpMethodsEnum.POST)) {
      requestBuilder.POST(HttpRequest.BodyPublishers.ofString(payload));
    }

    HttpResponse response = null;
    try {
      response = client.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return response;



  }
}
