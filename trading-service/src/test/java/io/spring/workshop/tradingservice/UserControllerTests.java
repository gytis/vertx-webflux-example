package io.spring.workshop.tradingservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TradingUserRepository repository;

    @Test
    public void listUsers() {
        TradingUser chantelle = new TradingUser("1", "cberardi", "Chantelle Berardi");
        TradingUser cordell = new TradingUser("2", "ckail", "Cordell Kail");

        BDDMockito.given(this.repository.findAll())
                .willReturn(Flux.just(chantelle, cordell));

        this.webTestClient.get().uri("/users").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBodyList(TradingUser.class)
                .hasSize(2)
                .contains(chantelle, cordell);

    }

    @Test
    public void showUser() {
        TradingUser chantelle = new TradingUser("1", "cberardi", "Chantelle Berardi");

        BDDMockito.given(this.repository.findByUserName("cberardi"))
                .willReturn(Mono.just(chantelle));

        this.webTestClient.get().uri("/users/cberardi").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(TradingUser.class)
                .isEqualTo(chantelle);
    }

}

