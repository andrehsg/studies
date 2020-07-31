package com.daitan.pocmiddleware;

import com.daitan.pocmiddleware.core.AmazonQueueService;
import com.daitan.pocmiddleware.modules.ProjectModule;
import com.daitan.pocmiddleware.resources.ProductResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ProducerAPIApplication extends Application<ProducerAPIConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ProducerAPIApplication().run(args);
    }

    @Override
    public String getName() {
        return "ProducerAPI";
    }

    @Override
    public void initialize(final Bootstrap<ProducerAPIConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final ProducerAPIConfiguration configuration,
                    final Environment environment) {

        try {
            Injector injector = Guice.createInjector(new ProjectModule());
            AmazonQueueService queueService = injector.getInstance(AmazonQueueService.class);
            environment.jersey().register(queueService);

            final ProductResource resource = new ProductResource(queueService);
            environment.jersey().register(resource);
        }

        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not start application");
        }
    }

}
