package io.spring.workshop.tradingservice;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UsersCommandLineRunner implements CommandLineRunner {

    private final TradingUserRepository repository;

    public UsersCommandLineRunner(TradingUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        List<TradingUser> users = Arrays.asList(
                new TradingUser("cberardi", "Chantelle Berardi"),
                new TradingUser("ckail", "Cordell Kail"),
                new TradingUser("xvierling", "Xochitl Vierling"),
                new TradingUser("amcnitt", "Albina Mcnitt"),
                new TradingUser("kding", "Kirstin Ding"),
                new TradingUser("jgalasso", "Jaunita Galasso"),
                new TradingUser("leccles", "Lonnie Eccles"),
                new TradingUser("rragon", "Reiko Ragon")
        );
        this.repository.insert(users).blockLast(Duration.ofSeconds(3));
    }

}
