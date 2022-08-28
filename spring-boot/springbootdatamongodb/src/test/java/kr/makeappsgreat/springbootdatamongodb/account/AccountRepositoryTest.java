package kr.makeappsgreat.springbootdatamongodb.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"spring.data.mongodb.host=localhost", "spring.mongodb.embedded.version=3.4.2"})
@DataMongoTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByEmail() {
        Account account = new Account();
        account.setUsername("Gayoun");
        account.setEmail("admin@makeappsgreat.kr");

        Account savedAccount = accountRepository.insert(account);

        assertThat(account == savedAccount).isTrue();
        System.out.println(">> " + savedAccount.getId());
        System.out.println(">> " + savedAccount.getUsername());
        System.out.println(">> " + savedAccount.getEmail());


        Optional<Account> byId = accountRepository.findById(savedAccount.getId());
        Optional<Account> byEmail = accountRepository.findByEmail(savedAccount.getEmail());

        assertThat(savedAccount == byId.get()).isFalse();
        assertThat(byId).isNotEmpty();
        assertThat(byId.get().getId()).isEqualTo(savedAccount.getId());

        assertThat(savedAccount == byEmail.get()).isFalse();
        assertThat(byEmail).isNotEmpty();
        assertThat(byEmail.get().getEmail()).isEqualTo(savedAccount.getEmail());
    }

}