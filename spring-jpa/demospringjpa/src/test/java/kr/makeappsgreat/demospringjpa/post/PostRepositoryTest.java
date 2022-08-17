package kr.makeappsgreat.demospringjpa.post;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(PostRepositoryTestConfig.class)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void crud() {
        // GIven
        Post post = new Post();
        post.setTitle("Hello, JPA!");
        post.setContent("안녕, JPA!");
        postRepository.save(post.publish());

        Predicate predicate = QPost.post.title.containsIgnoreCase("jpa");
        Optional<Post> one = postRepository.findOne(predicate);
        assertThat(one).isNotEmpty();
    }

    /* @Test
    public void event() {
        Post post = new Post();
        post.setTitle("Test a event.");

        PostPublishedEvent event = new PostPublishedEvent(post);

        applicationContext.publishEvent(event);
    } */
}