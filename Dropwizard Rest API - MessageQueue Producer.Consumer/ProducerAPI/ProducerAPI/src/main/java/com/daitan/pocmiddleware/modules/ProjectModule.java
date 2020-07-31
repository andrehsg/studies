package com.daitan.pocmiddleware.modules;

import com.daitan.pocmiddleware.core.AmazonQueueService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.net.URI;
import java.net.URISyntaxException;

public class ProjectModule extends AbstractModule {

  private static final String SQS_ROOT_URL = "http://localstack:4576";


  protected void configure() {

  }

  @Provides AmazonQueueService provideAmazonService () throws URISyntaxException {
    URI uri = new URI(SQS_ROOT_URL);
    return new AmazonQueueService(uri);

  }

}
