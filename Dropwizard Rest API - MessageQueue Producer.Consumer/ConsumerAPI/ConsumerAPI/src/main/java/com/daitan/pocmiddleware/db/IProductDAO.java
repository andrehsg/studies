package com.daitan.pocmiddleware.db;

import com.daitan.pocmiddleware.api.Product;

import java.util.List;

public interface IProductDAO {
  List<Product> findAll();
  Product findById(Integer id);
  void save(Product group);
  void remove(Product group);
  void createTable();
  void useKeySpace();


}
