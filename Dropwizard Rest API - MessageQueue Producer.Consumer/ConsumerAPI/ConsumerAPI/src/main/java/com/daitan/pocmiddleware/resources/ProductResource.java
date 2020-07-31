package com.daitan.pocmiddleware.resources;

import com.daitan.pocmiddleware.api.Product;
import com.daitan.pocmiddleware.api.ProductCache;
import com.daitan.pocmiddleware.db.ProductDAO;
import com.google.inject.Inject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v2")
public class ProductResource {

  ProductCache cacheProduct;
  @Inject
  public ProductResource(ProductCache cacheProduct) {
    this.cacheProduct = cacheProduct;
  }

  @GET
  @Path("/produtos")
  @io.dropwizard.jersey.caching.CacheControl(maxAge = 2, maxAgeUnit = TimeUnit.HOURS)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllProducts () throws InterruptedException {

    if (cacheProduct.getCache().size() != 0) {
      return Response.ok()
          .entity(cacheProduct.getProducts())
          .build();
    }

    Thread.sleep(5000);
    ProductDAO dao = new ProductDAO();

    List<Product> products = dao.findAll();
    products.stream()
        .forEach(item -> cacheProduct.insertProduct(item.getId(), item));

    return Response.ok()
        .entity(products).build();
  }

  @GET
  @Path("/produtos/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getProductById (@PathParam("id") Integer id) {

    ProductDAO dao = new ProductDAO();
    return Response.ok()
        .entity(dao.findById(id)).build();

  }
}
