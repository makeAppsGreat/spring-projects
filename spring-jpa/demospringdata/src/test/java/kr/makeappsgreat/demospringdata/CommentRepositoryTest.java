package kr.makeappsgreat.demospringdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

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
        Comment comment = new Comment();
        comment.setComment("Hello Comment");
        commentRepository.save(comment);

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
    public void save() {
        commentRepository.save(null);
    }

    @Test
    public void findAll() {
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isEmpty();
    }

}