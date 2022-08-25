package kr.makeappsgreat.studyspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SimpleRunner implements ApplicationRunner {

    private Logger looger = LoggerFactory.getLogger(SimpleRunner.class);

    @Value("${youn.name}")
    private String name;

    @Autowired
    private GayounProperties youn;

    @Autowired
    private String hello;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        looger.debug(">> Print foo-bar with ApplicationRunner.");

        looger.debug("Program argument foo : " + args.containsOption("foo"));
        looger.debug("Program argument bar : " + args.containsOption("bar"));

        looger.debug("With value annotation");
        looger.debug(">> youn.name : " + name);

        looger.debug("With ConfigurationProperties");
        looger.debug(">> youn.name : " + youn.getName());
        looger.debug(">> youn.age : " + youn.getAge());
        looger.debug(">> youn.fullName : " + youn.getFullName());

        looger.debug(">> " + hello);
    }
}
