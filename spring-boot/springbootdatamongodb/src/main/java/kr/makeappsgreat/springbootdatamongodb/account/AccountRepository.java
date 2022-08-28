package kr.makeappsgreat.springbootdatamongodb.account;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Account findByUsername(String username);

    Optional<Account> findByEmail(String email);
}
