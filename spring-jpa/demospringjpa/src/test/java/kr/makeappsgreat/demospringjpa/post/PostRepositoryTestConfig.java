package kr.makeappsgreat.demospringjpa.post;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostRepositoryTestConfig {

    @Bean
    public PostListener postListener() {
        return new PostListener();
    }

    @Bean
    public ApplicationListener<PostPublishedEvent> postPublishedEventListener() {

        return new ApplicationListener<PostPublishedEvent>() {
            @Override
            public void onApplicationEvent(PostPublishedEvent event) {
                System.out.println(">> \"" + event.getPost().getTitle() + "\"");
            }
        };
    }
}
