package kr.makeappsgreat.studyspringboot;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SimpleRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("\n>> Print foo-bar with ApplicationRunner.");

        System.out.println("Program argument foo : " + args.containsOption("foo"));
        System.out.println("Program argument bar : " + args.containsOption("bar"));
    }
}
