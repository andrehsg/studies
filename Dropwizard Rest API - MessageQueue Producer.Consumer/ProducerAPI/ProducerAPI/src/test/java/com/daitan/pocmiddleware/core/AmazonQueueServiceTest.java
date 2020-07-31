package com.daitan.pocmiddleware.core;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import software.amazon.awssdk.services.sqs.model.SetQueueAttributesResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class AmazonQueueServiceTest {

  private static final String SQS_ROOT_URL = "http://localhost:4576";
  private static final String MESSAGE_TEXT = "TEXT";


  @Test @Ignore
  public void testSendMsgInAQueue() throws URISyntaxException {

    String queueURL = SQS_ROOT_URL + "/queue/testSendMsgInAQueue";
    AmazonQueueService sqsService = new AmazonQueueService(new URI(SQS_ROOT_URL));
    CreateQueueResponse createresponse = sqsService.createQueue("testSendMsgInAQueue");
    assertEquals(queueURL, createresponse.queueUrl());
    SendMessageResponse msgResponse = sqsService.sendMessage(queueURL, MESSAGE_TEXT);
    assertEquals(200, msgResponse.sdkHttpResponse().statusCode());
    sqsService.deleteQueue(queueURL);
  }

  @Test @Ignore
  public void testReceiveMsgInAQueue() throws URISyntaxException {

    String queueURL = SQS_ROOT_URL + "/queue/testReceiveMsgInAQueue";
    AmazonQueueService sqsService = new AmazonQueueService(new URI(SQS_ROOT_URL));
    sqsService.createQueue("testReceiveMsgInAQueue");
    SetQueueAttributesResponse enablePollingResponse = sqsService.enableLongPolling(queueURL);
    assertEquals(200, enablePollingResponse.sdkHttpResponse().statusCode());
    sqsService.sendMessage(queueURL, MESSAGE_TEXT);
    sqsService.sendMessage(queueURL, MESSAGE_TEXT);
    List<Message> msgList = sqsService.receiveMessage(queueURL);
    assertEquals(2, msgList.size());
    sqsService.deleteQueue(queueURL);

  }

  @Test @Ignore
  public void deletemsgInAQueue() throws URISyntaxException {

    String queueURL = SQS_ROOT_URL + "/queue/testDeleteMsgInAQueue";
    AmazonQueueService sqsService = new AmazonQueueService(new URI(SQS_ROOT_URL));
    sqsService.createQueue("testDeleteMsgInAQueue");
    sqsService.sendMessage(queueURL, MESSAGE_TEXT);
    sqsService.sendMessage(queueURL, MESSAGE_TEXT);
    List<Message> msgList = sqsService.receiveMessage(queueURL);
    msgList.stream()
        .forEach(msg -> sqsService.deleteMessage(queueURL, msg));
    msgList = sqsService.receiveMessage(queueURL);
    assertEquals(0, msgList.size());
    sqsService.deleteQueue(queueURL);

  }
}
