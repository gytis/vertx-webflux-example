A Vert.x WebFlux integration example adapted from [webflux-workshop](https://github.com/bclozel/webflux-workshop).
It consists of two applications demonstrating the main aspects of WebFlux:
* Annotated controllers
* Functional endpoints
* WebClient
* WebSockets
* WebTestClient

Both applications use [vertx-spring-boot-http](https://github.com/snowdrop/vertx-spring-boot/tree/master/http) module,
which handles underlying HTTP server and client for WebFlux.

To begin with, in a first terminal window start the Stock Quotes application. It will start a web server on a port 8081
and expose a HTTP REST endpoint generating a constant stream of JSON objects with stock quotes.
```
cd stock-quotes
mvn spring-boot:run
```

Then, in a second terminal window start the Trading Service application. It will start a web server on a port 8080
and expose a web page to see live stock quotes graph, user database and will provide a form to access echo WebSocket endpoint.
```
cd trading-service
mvn spring-boot:run
```

Now, open Trading Service page with your browser at http://localhost:8080.
