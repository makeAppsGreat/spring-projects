package kr.makeappsgreat.demospringjpa.post;


import kr.makeappsgreat.demospringjpa.MyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {
public interface PostRepository extends MyRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
}
