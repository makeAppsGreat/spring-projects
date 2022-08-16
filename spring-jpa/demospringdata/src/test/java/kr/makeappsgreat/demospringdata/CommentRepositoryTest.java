package kr.makeappsgreat.demospringdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void crud() {
        // Given
        createComment("Hello Comment", 0);

        // When
        List<Comment> all = commentRepository.findAll();

        // Then
        assertThat(all.size()).isEqualTo(1);

        // When
        long count = commentRepository.count();

        // Then
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void findAll() {
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isEmpty();
    }

    @Test
    public void findByCommentContains() {
        // Given
        createComment("spring data jpa", 0);

        // When
        List<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("spring");

        // Then
        assertThat(comments.size()).isEqualTo(1);
        comments.forEach(System.out::println);
    }

    @Test
    public void findByCommentContainsWithPageable() {
        // Given
        createComment("spring JPA", 100);
        createComment("study Spring", 3);
        createComment("JAVA 1.8", 9);
        createComment("JAVA 11", 8);
        createComment("Spring Core", 10);
        createComment("Spring MVC", 7);

        // When
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "LikeCount"));
        /* Page<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("java", pageRequest);

        // Then
        assertThat(comments.getTotalElements()).isEqualTo(2);
        assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 9);
        comments.forEach(System.out::println); */

        try (Stream<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("java", pageRequest);) {
            Comment comment = comments.findFirst().get();
            assertThat(comment.getLikeCount()).isEqualTo(9);
        }
    }

    @Test
    public void findByLikeCountGreaterThan() {
        // Given
        createComment("good practice", 10);

        // When
        List<Comment> comments = commentRepository.findByLikeCountGreaterThan(8);

        // Then
        assertThat(comments.size()).isEqualTo(1);
        comments.forEach(System.out::println);
    }

    @Test
    public void findByCommentContainsOrderByLikeCountDesc() {
        // Given
        Comment topLike = createComment("spring JPA", 100);
        Comment minLike = createComment("study Spring", 3);
        createComment("JAVA 1.8", 9);
        createComment("JAVA 11", 8);
        createComment("Spring Core", 10);
        createComment("Spring MVC", 7);

        // When
        List<Comment> comments = commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountDesc("spring");

        // Then
        assertThat(comments.size()).isEqualTo(4);
        assertThat(comments.get(0).getComment()).isEqualTo(topLike.getComment());
        assertThat(comments).last().hasFieldOrPropertyWithValue("likeCount", minLike.getLikeCount());
        comments.forEach(System.out::println);
    }

    private Comment createComment(String comment, int likeCount) {
        Comment newComment = new Comment();
        newComment.setComment(comment);
        newComment.setLikeCount(likeCount);
        commentRepository.save(newComment);

        return newComment;
    }

}