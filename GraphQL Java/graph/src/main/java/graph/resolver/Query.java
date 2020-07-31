package graph.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import graph.api.Product;
import graph.client.HttpClientWrapper;
import graph.client.HttpMethodsEnum;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class Query implements GraphQLRootResolver {

  private static final String ENDPOINT_URL_ = "http://localhost:9189/v2/produtos";

  public Product product (int productId) {

    HttpResponse response = HttpClientWrapper.httpCall(HttpMethodsEnum.GET,
        ENDPOINT_URL_+"/"+productId, null);
    ObjectMapper objectMapper = new ObjectMapper();
    Product product = null;
    try {
      product = objectMapper.readValue(response.body().toString(), Product.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return product;
  }

  public List<Product> allProducts() {

    List<Product> list = null;
    HttpResponse response = HttpClientWrapper.httpCall(HttpMethodsEnum.GET, ENDPOINT_URL_, null);
    System.out.println(response.body());

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      list = objectMapper.readValue(response.body().toString(),
          objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }
}
