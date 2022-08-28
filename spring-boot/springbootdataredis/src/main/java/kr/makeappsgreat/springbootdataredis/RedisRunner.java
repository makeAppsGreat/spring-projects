package kr.makeappsgreat.springbootdataredis;

import kr.makeappsgreat.springbootdataredis.account.Account;
import kr.makeappsgreat.springbootdataredis.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisRunner implements ApplicationRunner {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /* ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set("Gayoun", "makeappsgreat");
        values.set("springboot", "2.7.3"); */

        /* Account account = new Account();
        account.setUsername("Gayoun");
        account.setEmail("makeappsgreat@gmail.com");

        Account savedAccount = accountRepository.save(account); */

        // Optional<Account> one = accountRepository.findById(savedAccount.getId()); //49eb58d8-2065-4ebe-8953-42f16bd50471
        Optional<Account> one = accountRepository.findById("49eb58d8-2065-4ebe-8953-42f16bd50471");
        System.out.println(">> " + one.get().getId());
        System.out.println(">> " + one.get().getUsername());
        System.out.println(">> " + one.get().getEmail());
    }
}
