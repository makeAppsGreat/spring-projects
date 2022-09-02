package kr.makeappsgreat.springbootjsp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {

    @GetMapping("/events")
    public String getEvents(Model model) {
        Event event = new Event();
        event.setName("스프링 웹 MVC 스터디 1");
        event.setStarts(LocalDateTime.of(2022, 9, 1, 14, 0, 0));

        Event event2 = new Event();
        event2.setName("스프링 웹 MVC 스터디 2");
        event2.setStarts(LocalDateTime.of(2022, 9, 2, 14, 0, 0));

        List<Event> events = List.of(event, event2);

        model.addAttribute("events", events);
        model.addAttribute("message", "풍요롭고 여유로운 한가위 보내세요.");

        return "events/list";
    }
}
