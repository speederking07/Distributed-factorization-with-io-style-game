package pl.zespolowe.splix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SplixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplixApplication.class, args);
    }
}
