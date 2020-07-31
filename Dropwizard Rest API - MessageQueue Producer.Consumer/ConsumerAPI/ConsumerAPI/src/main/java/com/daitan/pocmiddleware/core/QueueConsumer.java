package com.daitan.pocmiddleware.core;

import com.daitan.pocmiddleware.api.Product;
import com.daitan.pocmiddleware.db.IProductDAO;
import com.daitan.pocmiddleware.db.ProductDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.Message;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class QueueConsumer {

  @SneakyThrows(URISyntaxException.class)
  public CreateQueueResponse createQueue (String queueURL) {

    AmazonQueueService queueService = new AmazonQueueService(
        new URI(queueURL));
    CreateQueueResponse queueCreationResponse = queueService
        .createQueue("queue");
    return queueCreationResponse;
  }


  @SneakyThrows(URISyntaxException.class)
  public QueueConsumer readQueue (String queueURL) {

    AmazonQueueService queueService = new AmazonQueueService(
        new URI(queueURL));
    queueService.enableLongPolling(queueURL);
    IProductDAO productDAO = new ProductDAO();
    productDAO.useKeySpace();
    productDAO.createTable();
    ObjectMapper objectMapper = new ObjectMapper();
    while (true) {

      List<Message> messages = queueService.receiveMessage(queueURL);
      System.out.println("receiving.... msg size is " + messages.size());
      messages.forEach(
          item -> {
            try {
              Product product = objectMapper.readValue(item.body(), Product.class);
              if (product != null) {
                productDAO.save(product);
              }
                queueService.deleteMessage(queueURL, item);
            } catch (IOException e) {
              e.printStackTrace();
              System.out.println("Could not read data. Exception!");
            }
          });
    }

  }

}
