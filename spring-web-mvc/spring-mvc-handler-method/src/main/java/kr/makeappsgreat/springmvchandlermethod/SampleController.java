package kr.makeappsgreat.springmvchandlermethod;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UrlPathHelper;

@Controller
public class SampleController {

    @GetMapping("/events/{id}")
    @ResponseBody
    // public Event getEvent(@PathVariable Long id, @MatrixVariable String name) {
    public Event getEvent(@PathVariable Long id, @RequestParam String name) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);

        return event;
    }
}
