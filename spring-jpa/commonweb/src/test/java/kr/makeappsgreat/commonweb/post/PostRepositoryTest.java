package kr.makeappsgreat.commonweb.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void save() {
        Post post = new Post();
        // post.setId(1L);
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post); // Id is not exist. -> persist

        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(savedPost == post);

        Post updatePost = new Post();
        updatePost.setId(post.getId());
        updatePost.setTitle("hibernate");
        Post updatedPost = postRepository.save(post); // Id is exist. -> merge

        assertThat(entityManager.contains(updatedPost)).isTrue();
        assertThat(entityManager.contains(updatePost)).isFalse();
        assertThat(updatedPost == updatePost);

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void findByTitleStartsWith() {
        savePost("Spring Data JPA");

        List<Post> posts = postRepository.findByTitleStartsWith("Spring");
        assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    void findByTitle() {
        Post post = savePost("Spring");
        savePost("Spring");
        savePost("Spring");

        List<Post> posts = postRepository.findByTitle(post.getTitle(), JpaSort.unsafe("LENGTH(title)"));
        assertThat(posts.size()).isEqualTo(3);
    }

    @Test
    void updateTitle() {
        Post post = savePost("spring");

        String hibernate = "hibernate";
        /* int update = postRepository.updateTitle(hibernate, post.getId());
        assertThat(update).isEqualTo(1);

        Optional<Post> byId = postRepository.findById(post.getId());
        assertThat(byId.get().getTitle()).isEqualTo(hibernate); */

        post.setTitle(hibernate);
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(hibernate);
    }

    private Post savePost(String title) {
        Post post = new Post();
        post.setTitle(title);
        Post savedPost = postRepository.save(post);

        return savedPost;
    }
}