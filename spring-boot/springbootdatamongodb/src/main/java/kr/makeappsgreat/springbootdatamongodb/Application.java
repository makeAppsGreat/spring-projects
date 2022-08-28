package kr.makeappsgreat.springbootdatamongodb;

import kr.makeappsgreat.springbootdatamongodb.account.Account;
import kr.makeappsgreat.springbootdatamongodb.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

@SpringBootApplication
public class Application {

    /* @Autowired
    MongoTemplate mongoTemplate; */

    @Autowired
    AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return (args) -> {
            /* Account account = new Account();
            account.setUsername("Gayoun");
            account.setEmail("admin@makeappsgreat.kr");

            mongoTemplate.insert(account); */

            Account account = new Account();
            account.setUsername("youn");
            account.setEmail("makeappsgreat@gmail.com");

            Account savedAccount = accountRepository.insert(account);
            Optional<Account> one = accountRepository.findById(savedAccount.getId());

            System.out.println(">> " + one.get().getId());
            System.out.println(">> " + one.get().getUsername());
            System.out.println(">> " + one.get().getEmail());
        };
    }
}
