package kr.makeappsgreat.springmvchandlermethod;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice(assignableTypes = {EventController.class, EventApi.class})
public class BaseController {

    @InitBinder
    public void initEventBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
        webDataBinder.addValidators(new EventValidator());
    }

    // Add object to model
    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories", List.of("study", "seminar", "hobby", "social"));
    }

    /* @ModelAttribute("categories")
    public List<String> categories() {
        return List.of("study", "seminar", "hobby", "social");
    } */

    @ExceptionHandler({EventException.class, RuntimeException.class})
    public String eventErrorHandler(RuntimeException exception, Model model) {
        model.addAttribute("message", "Error raised.");

        return "error";
    }
}
