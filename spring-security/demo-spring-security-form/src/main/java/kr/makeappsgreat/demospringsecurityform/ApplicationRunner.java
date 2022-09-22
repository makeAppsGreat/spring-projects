package kr.makeappsgreat.demospringsecurityform;

import kr.makeappsgreat.demospringsecurityform.account.Account;
import kr.makeappsgreat.demospringsecurityform.account.AccountService;
import kr.makeappsgreat.demospringsecurityform.book.Book;
import kr.makeappsgreat.demospringsecurityform.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Autowired
    BookRepository bookRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account admin = new Account();
        admin.setUsername("admin");
        admin.setPassword("simple");
        admin.setRole("ADMIN");
        accountService.createNew(admin);

        Account user = createUser("youn");
        Account user2 = createUser("makeappsgreat");

        createBook("Spring", user);
        createBook("Hibernate", user2);
    }

    private Book createBook(String title, Account user) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(user);

        return bookRepository.save(book);
    }

    private Account createUser(String username) {
        Account user = new Account();
        user.setUsername(username);
        user.setPassword("simple");
        user.setRole("USER");

        return accountService.createNew(user);
    }
}
