package io.spring.workshop.tradingservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Controller
public class QuotesController {

    private final WebClient.Builder builder;

    // Use injected and preconfigured builder (with Vert.x connector) instead of a default client
    public QuotesController(WebClient.Builder builder) {
        this.builder = builder;
    }

    @GetMapping("/quotes")
    public String quotes() {
        return "quotes";
    }

    @GetMapping(path = "/quotes/feed", produces = TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Quote> quotesStream() {
        return builder.baseUrl("http://localhost:8081")
                .build()
                .get()
                .uri("/quotes")
                .accept(APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Quote.class)
                .share()
                .log("io.spring.workshop.tradingservice");
    }
}
