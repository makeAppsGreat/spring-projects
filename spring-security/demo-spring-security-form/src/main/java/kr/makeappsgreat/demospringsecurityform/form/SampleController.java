package kr.makeappsgreat.demospringsecurityform.form;

import kr.makeappsgreat.demospringsecurityform.account.Account;
import kr.makeappsgreat.demospringsecurityform.account.AccountContext;
import kr.makeappsgreat.demospringsecurityform.account.AccountRepository;
import kr.makeappsgreat.demospringsecurityform.account.UserAccount;
import kr.makeappsgreat.demospringsecurityform.book.BookRepository;
import kr.makeappsgreat.demospringsecurityform.common.CurrentUser;
import kr.makeappsgreat.demospringsecurityform.common.SecurityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    // public String index(Model model, Principal principal) {
    // public String index(Model model, @AuthenticationPrincipal UserAccount userAccount) {
    public String index(Model model, @CurrentUser Account account) {
        if (account == null) {
            model.addAttribute("message", "Hello, Spring Security!");
            model.addAttribute("username", null);
        } else {
            model.addAttribute("message", String.format("Hello, %s!", account.getUsername()));
            model.addAttribute("username", account.getUsername());
        }

        return "index";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "Info");

        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", String.format("Hello, %s!", principal.getName()));

        // AccountContext.setAccount(accountRepository.findByUsername(principal.getName()));
        sampleService.dashboard();

        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", String.format("Hello, admin %s!", principal.getName()));

        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("message", String.format("Hello, user %s!", principal.getName()));
        model.addAttribute("books", bookRepository.findCurrentUserBooks());

        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                SecurityLogger.log("Callable");

                return "Async Handler";
            }
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC, before async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");

        return "Async Service";
    }
}
