package kr.makeappsgreat.demospringsecurityform.form;

import kr.makeappsgreat.demospringsecurityform.account.Account;
import kr.makeappsgreat.demospringsecurityform.account.AccountContext;
import kr.makeappsgreat.demospringsecurityform.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    @Secured("ROLE_USER")
    public void dashboard() {
        /* Account account = AccountContext.getAccount();
        System.out.println(">> " + account.getUsername());
        System.out.println(">> " + account.getPassword()); */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println(">> " + authentication);
        System.out.println(">> " + userDetails.getUsername());
    }

    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called.");
    }
}
