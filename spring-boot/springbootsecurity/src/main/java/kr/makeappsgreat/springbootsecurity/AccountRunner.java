package kr.makeappsgreat.springbootsecurity;

import kr.makeappsgreat.springbootsecurity.account.Account;
import kr.makeappsgreat.springbootsecurity.account.AccountRepository;
import kr.makeappsgreat.springbootsecurity.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account user = accountService.createAccount("youn", "simple");
        System.out.println(">> " + user.getPassword());
    }
}
