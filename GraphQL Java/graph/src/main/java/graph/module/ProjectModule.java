package graph.module;

import com.coxautodev.graphql.tools.SchemaParser;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import graph.resolver.Mutation;
import graph.resolver.Query;
import graphql.schema.GraphQLSchema;

public class ProjectModule extends AbstractModule {

  protected void configure() {
    /*
    bind(GraphQLSchema.class).toInstance(SchemaParser.newParser()
        .file("schema.graphqls") //parse the schema file created earlier
        .resolvers(new Query(), new Mutation())
        .build()
        .makeExecutableSchema());
    */
  }
  @Provides
  public GraphQLSchema graphQLEndpoint() {
    return SchemaParser.newParser()
        .file("schema.graphqls") //parse the schema file created earlier
        .resolvers(new Query(), new Mutation())
        .build()
        .makeExecutableSchema();
  }


}
