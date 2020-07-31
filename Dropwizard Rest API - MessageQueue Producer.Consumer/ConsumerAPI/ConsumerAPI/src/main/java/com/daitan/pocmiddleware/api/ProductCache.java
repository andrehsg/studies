package com.daitan.pocmiddleware.api;



import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableCollection;

public class ProductCache {

  private Cache<Integer, Product> cache;

  public ProductCache () {
     this.cache = CacheBuilder.newBuilder().build();
  }

  public Product getProduct (Integer key) {
      return cache.getIfPresent(key);
  }

  public ImmutableCollection<Product> getProducts () {
      return cache.getAllPresent(cache.asMap().keySet())
          .values();
  }

  public void insertProduct (Integer key, Product product) {
    cache.put(key, product);
  }

  public Cache getCache () {
    return cache;
  }


}
