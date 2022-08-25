package kr.makeappsgreat.studyspringboot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:/test.properties")
@SpringBootTest
class ApplicationTest {

    @Autowired
    Environment environment;

    @Test
    public void contextLoads() {
        String name = environment.getProperty("youn.name");
        int age = Integer.valueOf(environment.getProperty("youn.age"));

        System.out.println(">> " + name + ", " + age);
        assertThat(name).isEqualTo("Gayoun Kim");
    }

}