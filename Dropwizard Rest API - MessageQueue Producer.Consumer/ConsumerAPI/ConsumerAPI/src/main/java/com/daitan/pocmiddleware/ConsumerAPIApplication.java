package com.daitan.pocmiddleware;

import com.daitan.pocmiddleware.api.ProductCache;
import com.daitan.pocmiddleware.core.AmazonQueueService;
import com.daitan.pocmiddleware.core.QueueConsumer;
import com.daitan.pocmiddleware.modules.ProductModule;
import com.daitan.pocmiddleware.resources.ProductResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.java.Log;
import software.amazon.awssdk.services.sqs.model.SqsException;

@Log
public class ConsumerAPIApplication extends Application<ConsumerAPIConfiguration> {

    private static final String SQS_ROOT_URL = "http://localstack:4576";

    public static void main(final String[] args) throws Exception {
        new ConsumerAPIApplication().run(args);
        QueueConsumer queueConsumer = new QueueConsumer();
        try {
            queueConsumer.createQueue(SQS_ROOT_URL + "/queue/queue");
            queueConsumer.readQueue(SQS_ROOT_URL + "/queue/queue");
        }
        catch (SqsException e) {
            log.info(e.awsErrorDetails().errorMessage());
        }
    }

    @Override
    public String getName() {
        return "ConsumerAPI";
    }

    @Override
    public void initialize(final Bootstrap<ConsumerAPIConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ConsumerAPIConfiguration configuration,
                    final Environment environment) {
        Injector injector = Guice.createInjector(new ProductModule());
        ProductCache cache = injector.getInstance(ProductCache.class);
        final ProductResource resource = new ProductResource(cache);
        environment.jersey().register(resource);

    }

}
