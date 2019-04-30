package io.spring.workshop.tradingservice.websocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EchoWebSocketHandlerTests {

    // Autowire client from Vert.x ClientAutoConfiguration instead of a default one
    @Autowired
    private WebSocketClient client;

    @LocalServerPort
    private String port;

    @Test
    public void echo() throws Exception {
        int count = 4;
        Flux<String> input = Flux.range(1, count).map(index -> "msg-" + index);
        ReplayProcessor<Object> output = ReplayProcessor.create(count);

        client.execute(getUrl("/websocket/echo"),
                session -> session
                        .send(input.map(session::textMessage))
                        .thenMany(session.receive().take(count).map(WebSocketMessage::getPayloadAsText))
                        .subscribeWith(output)
                        .then())
                .block(Duration.ofMillis(5000));

        assertEquals(input.collectList().block(Duration.ofMillis(5000)),
                output.collectList().block(Duration.ofMillis(5000)));
    }

    protected URI getUrl(String path) throws URISyntaxException {
        return new URI("ws://localhost:" + this.port + path);
    }
}
