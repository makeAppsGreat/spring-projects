package kr.makeappsgreat.springwebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class SampleController {

    @RequestMapping("/hello/{name:[A-Za-z]+}")
    @ResponseBody
    public String helloWithName(@PathVariable String name) {
        return String.format("Hello, %s!", name);
    }

    @RequestMapping(value = "/hello", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/hello", headers = HttpHeaders.FROM + "=localhost")
    @ResponseBody
    public String helloWithHeader() {
        return "hello";
    }

    @RequestMapping(value = "/hello", params = "name")
    @ResponseBody
    public String helloWithParam(@RequestParam String name) {
        return String.format("Hello, %s!", name);
    }
}
