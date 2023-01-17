package testmate.rickandmortiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RickAndMortiAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RickAndMortiAppApplication.class, args);
    }

}
