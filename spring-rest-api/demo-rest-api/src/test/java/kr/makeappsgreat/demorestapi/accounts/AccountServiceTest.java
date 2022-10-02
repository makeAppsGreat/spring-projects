package kr.makeappsgreat.demorestapi.accounts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void findByUsername() {
        // Given
        String email = "test@makeappsgreat.kr";
        String password = "simple";

        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(Set.of(AccountRole.USER, AccountRole.ADMIN))
                .build();
        accountService.saveAccount(account);

        // When
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Then
        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test
    public void findByBadUsername() {
        assertThrows(UsernameNotFoundException.class, () -> {
            accountService.loadUserByUsername("notalloweduser@makeappsgreat.kr");
        });
    }

}