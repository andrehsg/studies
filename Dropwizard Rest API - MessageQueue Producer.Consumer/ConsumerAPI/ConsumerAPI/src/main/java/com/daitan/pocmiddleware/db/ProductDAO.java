package com.daitan.pocmiddleware.db;

import com.daitan.pocmiddleware.api.Product;
import com.daitan.pocmiddleware.util.CassandraConnector;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
public class ProductDAO implements IProductDAO {
  private Session session;
  private static final String TABLE_NAME = "Product";

  public ProductDAO() {
    final String ipAddress = "cassandraDB";
    final int port = 9042;
    System.out.println("Connecting to IP Address " + ipAddress + ":" + port + "datacenter1");
    CassandraConnector.connect(ipAddress, port);
    this.session = CassandraConnector.getSession();
  }

  @Override
  public void useKeySpace() {

    KeyspaceDAO sr = new KeyspaceDAO();
    sr.createKeyspace("product_keyspace", "SimpleStrategy", 1);
    sr.useKeyspace("product_keyspace");
  }
  
  @Override
  public void createTable () {
    final String createMovieCql =
        "CREATE TABLE IF NOT EXISTS product_keyspace."+ TABLE_NAME + " (id int, description varchar, name varchar , PRIMARY KEY (id, name))";
    this.session.execute(createMovieCql);
  }

  @Override
  public List<Product> findAll() {
    ResultSet result =
        session.execute("SELECT * FROM product_keyspace." + TABLE_NAME + ";");

    List<Product> products = new ArrayList<>();

    result.forEach(
        rs -> {
          Product prod = new Product();
          prod.setId(rs.getInt("id"));
          prod.setDescription(rs.getString("description"));
          prod.setName(rs.getString("name"));
          products.add(prod);
        }
    );

    return products;
  }

  @Override
  public Product findById(Integer id) {
    ResultSet result =
        session.execute("SELECT * FROM product_keyspace." + TABLE_NAME
            + " WHERE id = " + id + "; ");
    List<Row> list = result.all();
    if (list.size() >= 1) {
      Product prod = new Product();
      prod.setId(list.get(0).getInt("id"));
      prod.setDescription(list.get(0).getString("description"));
      prod.setName(list.get(0).getString("name"));
      return prod;
    }
    else throw new BadRequestException();
  }

  @Override
  public void save(Product product) {

    StringBuilder sb = new StringBuilder("INSERT INTO product_keyspace.")
        .append(TABLE_NAME).append("(id, description, name) ")
        .append("VALUES (").append(product.getId())
        .append(", '")
        .append(product.getDescription())
        .append("', '")
        .append(product.getName())
        .append("');");
    String query = sb.toString();
    session.execute(query);
    System.out.println("Saved to DB: " + product.toString());
  }

  @Override
  public void remove(Product group) {

    //TODO

  }
}