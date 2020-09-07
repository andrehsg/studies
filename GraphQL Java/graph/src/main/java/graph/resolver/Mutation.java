package graph.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import graph.api.ProductInput;
import graph.client.HttpClientWrapper;
import graph.client.HttpMethodsEnum;

import java.io.IOException;
import java.net.http.HttpResponse;

public class Mutation implements GraphQLRootResolver {


  public String createProduct (ProductInput product) {
    return "TODO";
  }
}
