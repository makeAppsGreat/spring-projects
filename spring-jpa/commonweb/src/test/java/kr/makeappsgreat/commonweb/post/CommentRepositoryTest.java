package kr.makeappsgreat.commonweb.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
// @DataJpaTest
@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void getCommentWithEntityGraph() {
        commentRepository.getCommentById(1L);
        System.out.println("========================================");
        commentRepository.findById(1L);
    }

    @Test
    public void getComment() {
        Post post = new Post();
        post.setTitle("JPA");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("Some comment");
        comment.setPost(savedPost);
        comment.setUp(10);
        comment.setDown(1);
        commentRepository.save(comment);

        commentRepository.findByPost_Id(savedPost.getId(), CommentOnly.class).forEach(c -> {
            System.out.println("========================================");
            // System.out.println(c.getVotes());
            System.out.println(c.getComment());
        });
    }

    @Test
    public void specs() {
        commentRepository.findAll(CommentSpecs.isBest().and(CommentSpecs.isBest()), PageRequest.of(0, 10));
    }

    @Test
    public void queryByExample() {
        Comment prove = new Comment();
        prove.setBest(true);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIncludeNullValues()
                .withIgnorePaths("up", "down");

        Example<Comment> example = Example.of(prove, matcher);

        commentRepository.findAll(example);
    }
}