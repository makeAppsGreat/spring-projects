package kr.makeappsgreat.springmvchandlermethod;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventApi {

    // Using @RequestBody
    /* @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return event;
    } */

    // Using @RequestBody with @Valid
    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);

            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .body(event);
    }

    // Using HttpEntity
    /* @PostMapping
    public Event createEvent(HttpEntity<Event> request) {
        MediaType contentType = request.getHeaders().getContentType();
        System.out.println(">> " + contentType);

        return request.getBody();
    } */
}
