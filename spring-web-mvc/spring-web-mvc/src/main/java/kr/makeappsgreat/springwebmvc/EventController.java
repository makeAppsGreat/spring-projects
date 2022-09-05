package kr.makeappsgreat.springwebmvc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @GetMapping("/events")
    public String events() {
        return "events";
    }

    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postEvents() {
        return "{\"name\":\"events\"}";
    }

    @RequestMapping(value = "/events/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String events(@PathVariable Long id) {
        return id.toString();
    }

    @PutMapping(value = "/events/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void putEvents(@PathVariable Long id) {

    }
}
