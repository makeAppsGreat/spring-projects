package kr.makeappsgreat.commonweb.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
// @SpringBootTest
// @AutoConfigureMockMvc
@DataJpaTest
class PostControllerTest {

    @Autowired
    private PostRepository postRepository;

    // @Autowired
    // private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void crud() {
        createPost("jpa");

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

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

    /* @Test
    public void getPost() throws Exception {
        Post post = createPost("jpa");

        mockMvc.perform(get("/posts/" + post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(post.getTitle()));
    }

    @Test
    public void getPosts() throws Exception {
        for(int i = 0; i < 100; i++) {
            createPost(String.format("jpa (%d)", i));
        }

        mockMvc.perform(get("/posts")
                        .param("page", "3")
                        .param("size", "10")
                        .param("sort", "created,desc")
                        .param("sort", "title"))
                .andDo(print())
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$.content[0].title", is(post.getTitle())));
    } */

    private Post createPost(String title) {
        Post post = new Post();
        post.setTitle(title);

        postRepository.save(post);


        return post;
    }
}