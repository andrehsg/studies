package graph.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import graph.api.ProductInput;
import graph.client.HttpClientWrapper;
import graph.client.HttpMethodsEnum;

import java.io.IOException;
import java.net.http.HttpResponse;

public class Mutation implements GraphQLRootResolver {

  private static final String ENDPOINT_URL = "http://localhost:9179/v1/produtos";

  public Integer createProduct (ProductInput product) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    HttpResponse response = HttpClientWrapper.httpCall(HttpMethodsEnum.POST, ENDPOINT_URL,
        objectMapper.writeValueAsString(product));
    System.out.println(response.toString());
    return response.statusCode();
  }
}
