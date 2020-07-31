package com.daitan.pocmiddleware.api;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Product {

  private int id;
  private String name;
  private String description;


  public Product () {

  }
}
