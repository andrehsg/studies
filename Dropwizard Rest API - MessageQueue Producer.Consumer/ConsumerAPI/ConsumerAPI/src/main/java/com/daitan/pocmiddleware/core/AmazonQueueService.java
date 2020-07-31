package com.daitan.pocmiddleware.core;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageResponse;
import software.amazon.awssdk.services.sqs.model.DeleteQueueRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.SetQueueAttributesResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmazonQueueService {

  private SqsClient client;
  private static final int MAX_NUMBER_MESSAGES_RECEIVED = 10;

  public AmazonQueueService(URI endpoint) {
    client = SqsClient.builder()
        .region(Region.US_EAST_1)
        .credentialsProvider(() -> AwsBasicCredentials.create(
            "foobar",
            "foobar"))
        .endpointOverride(endpoint)
        .build();
  }

  public List<Message> receiveMessage(String queueUrl) {
    return client.receiveMessage(ReceiveMessageRequest.builder()
        .queueUrl(queueUrl)
        .maxNumberOfMessages(MAX_NUMBER_MESSAGES_RECEIVED)
        .build())
        .messages();
  }

  public CreateQueueResponse createQueue(String queueName) {
    return client.createQueue(CreateQueueRequest.builder()
        .queueName(queueName)
        .build());
  }

  public SendMessageResponse sendMessage(String queueUrl, String msg) {
    return client.sendMessage(SendMessageRequest.builder()
        .queueUrl(queueUrl)
        .messageBody(msg)
        .build());
  }

  public DeleteMessageResponse deleteMessage(String queueUrl, Message message) {
    return client.deleteMessage(DeleteMessageRequest.builder()
        .queueUrl(queueUrl)
        .receiptHandle(message.receiptHandle())
        .build());
  }

  public void deleteQueue(String queueUrl) {

    if (client.listQueues().queueUrls().contains(queueUrl)) {
      client.deleteQueue(DeleteQueueRequest.builder()
          .queueUrl(queueUrl)
          .build());
    }
  }

  public SetQueueAttributesResponse enableLongPolling (String queueUrl) {

    Map<String, String> attributes = new HashMap<>();
    attributes.put("ReceiveMessageWaitTimeSeconds", "20");
    return client.setQueueAttributes(SetQueueAttributesRequest.builder()
        .queueUrl(queueUrl)
        .attributesWithStrings(attributes)
        .build());
  }

}
