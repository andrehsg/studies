# ConsumerAPI

How to start the ConsumerAPI application
---

1. Run `mvn package` to build your application
1. Start application with `java -cp target/ConsumerAPI-1.0-SNAPSHOT.jar server config.yml`
1. App is run at`http://localhost:9189

--Using Docker
1. Run `mvn package` to build your application
2. docker build -t imagem_consumer .
3. docker compose -up
4. App is run at`http://localhost:9189
