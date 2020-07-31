package graph.api;

import lombok.Data;

@Data
public class ProductInput {

  private int id;
  private String name;
  private String description;

  public ProductInput(int id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public ProductInput() {

  }
}
