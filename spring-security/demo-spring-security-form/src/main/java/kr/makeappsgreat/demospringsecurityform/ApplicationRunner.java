package kr.makeappsgreat.demospringsecurityform;

import kr.makeappsgreat.demospringsecurityform.account.Account;
import kr.makeappsgreat.demospringsecurityform.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account user = new Account();
        user.setUsername("youn");
        user.setPassword("simple");
        user.setRole("USER");
        accountService.createNew(user);

        Account admin = new Account();
        admin.setUsername("admin");
        admin.setPassword("simple");
        admin.setRole("ADMIN");
        accountService.createNew(admin);
    }
}
