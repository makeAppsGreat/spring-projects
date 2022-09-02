package kr.makeappsgreat.springbootweb;

import org.springframework.web.bind.annotation.*;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public String hello(@RequestParam("id") Person person) {
        return String.format("Hello, %s!", person.getName());
    }

    @GetMapping("/message")
    public String message(@RequestBody String body) {
        return String.format("Hello, %s!", body);
    }

    @GetMapping("/jsonMessage")
    public Person jsonMessage(@RequestBody Person person) {
        return person;
    }
}
