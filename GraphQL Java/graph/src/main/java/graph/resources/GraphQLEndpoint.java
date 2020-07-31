package graph.resources;

import com.google.inject.Inject;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

  @Inject
  public GraphQLEndpoint(GraphQLSchema schema) {
    super(schema);
  }
}