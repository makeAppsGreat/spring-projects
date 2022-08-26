package kr.makeappsgreat.springbootwebmvc;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
public class SampleController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "Gayoun");

        return "hello";
    }

    @GetMapping("/exception")
    public String exception() {
        throw new SampleException();
    }

    @ExceptionHandler(SampleException.class)
    public @ResponseBody ResponseEntity<AppError> sampleError(SampleException e) {
        AppError appError = new AppError();
        appError.setMessage("error.app.key");
        appError.setReason("IDK");

        return ResponseEntity.internalServerError()
                .contentType(MediaType.APPLICATION_JSON)
                .body(appError);
    }

    @GetMapping("/helloHateoas")
    public @ResponseBody EntityModel<Hello> helloHateoas() {
        Hello hello = new Hello();
        hello.setPrefix("Hello,");
        hello.setName("Gayoun");

        return EntityModel.of(hello)
                .add(linkTo(methodOn(SampleController.class).helloHateoas()).withSelfRel());
    }
}
