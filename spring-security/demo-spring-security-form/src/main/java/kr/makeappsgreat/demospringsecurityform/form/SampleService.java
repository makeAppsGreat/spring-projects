package kr.makeappsgreat.demospringsecurityform.form;

import kr.makeappsgreat.demospringsecurityform.account.Account;
import kr.makeappsgreat.demospringsecurityform.account.AccountContext;
import kr.makeappsgreat.demospringsecurityform.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    public void dashboard() {
        Account account = AccountContext.getAccount();
        System.out.println(">> " + account.getUsername());
        System.out.println(">> " + account.getPassword());
    }

    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called.");
    }
}
