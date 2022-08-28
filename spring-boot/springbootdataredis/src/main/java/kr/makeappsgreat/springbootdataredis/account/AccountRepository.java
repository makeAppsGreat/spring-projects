package kr.makeappsgreat.springbootdataredis.account;


import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account, String> {
}
