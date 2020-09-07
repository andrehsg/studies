# graph

How to start the graph application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/graph-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8082`


GraphQL schema, input and types
---
input ProductInput {
  id: Int!
  name: String!
  description: String!
}

type Product {
  id: Int!
  name: String!
  description: String!
}

type Query {
  allProducts: [Product]
  product(id: Int!): Product
}

type Mutation {
    createProduct(productInput: ProductInput): Int
}
---

Testing (try on postman)
1. Send a POST to http://localhost:8082/graphql with content type GraphQL
2. Add the body
mutation {
    createProduct(productInput: {
          id: 1,
          name: "Hello",
          description: "Description"
    })


}
3. Result is "TODO". There is nothing special with this api, but I will add more logic later to carry on the studies.


