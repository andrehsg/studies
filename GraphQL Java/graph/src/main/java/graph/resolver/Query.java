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
    return null;
  }

  public List<Product> allProducts() {
    return null;
  }
}
