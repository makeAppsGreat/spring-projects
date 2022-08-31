package kr.makeappsgreat.springmvcdemo;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    public List<Event> getEvents() {
        Event event = Event.builder()
                .name("스프링 웹 MVC 스터디 1차")
                .limitOfEnrollment(10)
                .startDatetime(LocalDateTime.of(2022, 8, 30, 18, 0))
                .endDatetime(LocalDateTime.of(2022, 8, 30, 20, 0))
                .build();

        Event event2 = Event.builder()
                .name("스프링 웹 MVC 스터디 2차")
                .limitOfEnrollment(10)
                .startDatetime(LocalDateTime.of(2022, 9, 6, 18, 0))
                .endDatetime(LocalDateTime.of(2022, 9, 6, 20, 0))
                .build();

        return List.of(event, event2);
    }
}
