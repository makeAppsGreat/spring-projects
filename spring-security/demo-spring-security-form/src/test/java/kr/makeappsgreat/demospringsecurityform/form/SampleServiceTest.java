package kr.makeappsgreat.demospringsecurityform.form;

import kr.makeappsgreat.demospringsecurityform.account.Account;
import kr.makeappsgreat.demospringsecurityform.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    public void dashboard() {
        String username = "user";
        String password = "simple";

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole("ADMIN");
        accountService.createNew(account);

        UserDetails userDetails = accountService.loadUserByUsername(username);
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getPassword());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password);
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        sampleService.dashboard();
    }

}