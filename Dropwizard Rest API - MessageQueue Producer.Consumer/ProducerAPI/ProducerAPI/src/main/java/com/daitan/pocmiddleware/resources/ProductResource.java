package com.daitan.pocmiddleware.resources;

import com.daitan.pocmiddleware.api.Product;
import com.daitan.pocmiddleware.api.Status;
import com.daitan.pocmiddleware.core.AmazonQueueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

  private AmazonQueueService amazonQueueService;

  @Inject
  public ProductResource (AmazonQueueService amazonQueueService) {
    this.amazonQueueService = amazonQueueService;
  }

  @POST
  @Path("/produtos")
  public Response addProduct (Product product) throws JsonProcessingException {

    CreateQueueResponse queueCreationResponse = this.amazonQueueService
        .createQueue("queue");
    ObjectMapper objectMapper = new ObjectMapper();
    SendMessageResponse sendMsgResponse = this.amazonQueueService.sendMessage(
        queueCreationResponse.queueUrl(),
        objectMapper.writeValueAsString(product));
    System.out.println ("Message sent to Queue with msg id: " + sendMsgResponse.messageId());
    Status status = new Status();
    status.setStatus("queued");
    return Response.accepted()
        .entity(status)
        .build();

  }
}
