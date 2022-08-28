package kr.makeappsgreat.springbootdatajpa.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void connection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(">> " + metaData.getDriverName());
            System.out.println(">> " + metaData.getURL());
            System.out.println(">> " + metaData.getUserName());
        }
    }

    @Test
    public void di() throws SQLException {
        Account account = new Account();
        account.setUsername("Gayoun");
        account.setPassword("simple");

        Account savedAccount = accountRepository.save(account);
        assertThat(savedAccount).isNotNull();

        Optional<Account> existingAccount = accountRepository.findByUsername(savedAccount.getUsername());
        assertThat(existingAccount).isNotEmpty();

        Optional<Account> nonExistingAccount = accountRepository.findByUsername("youn");
        assertThat(nonExistingAccount).isEmpty();
    }

}