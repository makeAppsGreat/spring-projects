package kr.makeappsgreat.studyspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SampleListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    ApplicationArguments arguments;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println(">> onApplicationEvent called");

        System.out.println("System argument foo : " + (System.getProperty("foo") == null ? false : true));
        System.out.println("System argument bar : " + (System.getProperty("bar") == null ? false : true));
        System.out.println("Program argument foo : " + arguments.containsOption("foo"));
        System.out.println("Program argument bar : " + arguments.containsOption("bar"));
    }
}
