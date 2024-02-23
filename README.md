# planner

Sample Quarkus REST service that stores schedules as tasks and assignments.

Warning: This service is meant as a demo/tutorial so it does not require any HTTP authentication. Authentication could be configured in the load balancer or application gateway, or added through CDI annotation if needed. 

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Technical concepts

 - The data is stored in a Postgres SQL database
 - The service endpoints are implemented in 'resource' classes using Jakarta annotations
 - The endpoints and DB layer are reactive, based on Mutiny
 - The DB schema is maintained through Flyway
 - REST service testing is accomplished through QuarkusTest and the restassured library
 
## Creation process

This service was created using the following procedure:

The project was first created using the web tool at https://code.quarkus.io
 - Select group and artifact name
 - Select components - in this case, resteasy-reactive-jackson, flyway, postgres (reactive + jdbc for flyway)
The result can be summarized as https://code.quarkus.io/?g=com.l2o&a=planner&e=resteasy-reactive-jackson&e=jdbc-postgresql&e=flyway&e=hibernate-reactive&e=reactive-pg-client

Install dependencies: IDE, Java 17+, Docker Desktop (required for QuarkusTests)

Implement DB entities, DTOs, services and resources.
 - The resource class/methods should be annotated with `@Path`, `@GET`, `@POST` etc. to expose them for HTTP calls
 - The service classes should be annotated as ApplicationScoped (application-wide singleton) and injected though `@Inject` in the resource classes, or through a single constructor. 
 - All methods return Uni<...> values.
    + These can be chained to transform the results using map and the likes
    + The actions will not be actually be performed until these are subscribed (which quarkus does whenever a REST endpoints returns a Uni)
    + For database access, a Mutiny.SessionFactory should be injected in the service and used for all access - because the call is not synchronous, the transaction cannot be delimited using the `@Transactional` annotation so instead, we must use `withSession` or `withTransaction` to obtain the session and/or transaction and use it.

Implement unit tests. By default, Quarkus will detect all the SQL entities and create the tables on start (we can later move to Flyway once the schema is settled - the SQL statements can be logged by setting the `quarkus.hibernate-orm.log.sql`to true in the test profile properties).

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Flyway ([guide](https://quarkus.io/guides/flyway)): Handle your database schema migrations
- Reactive PostgreSQL client ([guide](https://quarkus.io/guides/reactive-sql-clients)): Connect to the PostgreSQL database using the reactive pattern
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
