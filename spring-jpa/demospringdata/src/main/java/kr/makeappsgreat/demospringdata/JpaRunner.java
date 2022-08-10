package kr.makeappsgreat.demospringdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("makeappsgreat");
        account.setPassword("hibernate");

        Account account2 = new Account();
        account2.setUsername("youn");
        account2.setPassword("hibernate");

        Study study = new Study();
        study.setName("Spring DATA JPA");

        Study study2 = new Study();
        study2.setName("Spring Core");

        Study study3 = new Study();
        study3.setName("Spring REST Api");

        /* account.getStudies().add(study);
        account.getStudies().add(study2);
        account2.getStudies().add(study3); */
        account.addStudy(study);
        account.addStudy(study2);
        account2.addStudy(study3);


        Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.save(account2);
        session.save(study);
        session.save(study2);
        session.save(study3);


        Account youn = session.load(Account.class, account2.getId());
        System.out.println("================================================================================");
        System.out.println(youn.getUsername());
    }
}
