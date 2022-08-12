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
        /* Post post = new Post();
        post.setTitle("Spring Data JPA 강좌 질문");

        Comment comment = new Comment();
        comment.setComment("ㅇㅇ");
        post.addComment(comment);

        Comment comment2 = new Comment();
        comment2.setComment("ㄴㄴ");
        post.addComment(comment2); */


        Session session = entityManager.unwrap(Session.class);
        // session.save(post);

        Post post1 = session.get(Post.class, 4L);
        // session.delete(post1);
        System.out.println(">> " + post1.getTitle());

        /* Comment comment3 = session.get(Comment.class, 5L);
        System.out.println(comment3.getComment()); */

        post1.getComments().forEach(c -> {
           System.out.println("  >> " + c.getComment());
        });

    }
}
