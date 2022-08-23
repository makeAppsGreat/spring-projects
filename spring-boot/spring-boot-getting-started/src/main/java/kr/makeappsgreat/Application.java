package kr.makeappsgreat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // SpringApplication.run(Application.class, args);

        SpringApplication application = new SpringApplication(Application.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    /* @Bean
    public Holoman holoman() {
        Holoman holoman = new Holoman();
        holoman.setName("Youn");
        holoman.setHowLong(10);

        return holoman;
    } */
}
