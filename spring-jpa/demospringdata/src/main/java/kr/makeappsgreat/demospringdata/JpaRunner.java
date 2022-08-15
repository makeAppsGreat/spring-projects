package kr.makeappsgreat.demospringdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /* Post post = new Post();
        post.setTitle("Spring JPA");

        Comment comment = new Comment();
        comment.setComment("ㄱㄱ");
        post.getComments().add(comment);

        postRepository.save(post); */
        

        // postRepository.findAll().forEach(System.out::println);
        postRepository.findAll().forEach(p -> {
            System.out.println(p.getTitle());
            p.getComments().forEach(System.out::println);
        });
    }
}
