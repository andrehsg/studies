package graph;

import com.google.inject.Guice;
import com.google.inject.Injector;
import graph.module.ProjectModule;
import graph.resources.GraphQLEndpoint;
import graphql.schema.GraphQLSchema;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class graphApplication extends Application<graphConfiguration> {


    public static void main(final String[] args) throws Exception {
        new graphApplication().run(args);
    }

    @Override
    public String getName() {
        return "graph";
    }

    @Override
    public void initialize(final Bootstrap<graphConfiguration> bootstrap) {
        // TODO: application initialization
    }


    @Override
    public void run(final graphConfiguration configuration,
                    final Environment environment) {

        Injector injector = Guice.createInjector(new ProjectModule());

        final GraphQLEndpoint servlet = new GraphQLEndpoint(injector.getInstance(GraphQLSchema.class));
        environment.servlets().addServlet("graphql", servlet)
            .addMapping("/graphql");
    }

}
