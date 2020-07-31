package graph.api;

import lombok.Data;

@Data
public class Product {

  private int id;
  private String name;
  private String description;

  public Product (Integer id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public Product() {

  }
}
