package com.daitan.pocmiddleware.resources;

import static org.junit.Assert.assertEquals;

import com.daitan.pocmiddleware.api.Product;
import com.daitan.pocmiddleware.core.AmazonQueueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

public class ProducerResourceTest {

  @Test @Ignore
  public void addProductPost () throws URISyntaxException, JsonProcessingException {

    ProductResource resource = new ProductResource(new AmazonQueueService(
        new URI("http://localhost:4576")
    ));
    Product product = new Product();
    product.setId(1);
    product.setName("anyName");
    product.setDescription("anything");
    Response response = resource.addProduct(product);
    assertEquals(202, response.getStatus());
  }


}
