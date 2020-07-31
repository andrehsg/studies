package com.daitan.pocmiddleware.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {

  private int id;
  private String name;
  private String description;

  public Product() {

  }
}
