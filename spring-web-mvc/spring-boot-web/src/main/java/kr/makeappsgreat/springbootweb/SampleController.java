package kr.makeappsgreat.springbootweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") Person person) {
        return String.format("Hello, %s!", person.getName());
    }

    @GetMapping("/hello")
    public String helloWithParam(@RequestParam("id") Person person) {
        return String.format("Hello, %s!", person.getName());
    }
}
