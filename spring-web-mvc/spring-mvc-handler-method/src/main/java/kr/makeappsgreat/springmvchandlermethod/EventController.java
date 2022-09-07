package kr.makeappsgreat.springmvchandlermethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("event")
public class EventController {

    // Unused Mapping
    /* @PostMapping("/events")
    // public Event getEvent(@RequestParam String name, @RequestParam Integer limit) {
    public String getEvent(@Validated @ModelAttribute Event event, BindingResult bindingResult, SessionStatus sessionStatus) {
        // Event event = new Event();
        // event.setName(name);
        // event.setLimit(limit);

        if (bindingResult.hasErrors()) return "/events/form";

        // Save an event to DB
        sessionStatus.setComplete();

        return "redirect:/events/list";
    } */

    @GetMapping("/events/{id}")
    @ResponseBody
    // public Event getEvent(@PathVariable Long id, @MatrixVariable String name) {
    public Event getEventWithIdPath(@PathVariable Long id, @RequestParam String name) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);

        return event;
    }

    @GetMapping("/events/form/name")
    public String eventsFormName(Model model) {
        throw new EventException();
        // model.addAttribute("event", new Event());

        // return "/events/form-name";
    }

    @PostMapping("/events/form/name")
    public String eventsFormNameSubmit(@Validated @ModelAttribute Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/events/form-name";

        return "redirect:/events/form/limit";
    }

    @GetMapping("/events/form/limit")
    public String eventsFormLimit(@ModelAttribute Event event, Model model) {
        model.addAttribute("event", event);

        return "/events/form-limit";
    }

    @PostMapping("/events/form/limit")
    public String eventsFormLimitSubmit(@Validated @ModelAttribute Event event,
                                        BindingResult bindingResult,
                                        SessionStatus sessionStatus,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "/events/form-limit";

        /* redirectAttributes.addAttribute("name", event.getName());
        redirectAttributes.addAttribute("limit", event.getLimit()); */
        redirectAttributes.addFlashAttribute("newEvent", event);

        sessionStatus.setComplete();

        return "redirect:/events/list";
    }

    @GetMapping("/events/list")
    public String getEvents(/* @RequestParam String name,
                            @RequestParam Integer limit, */
                            Model model,
                            @SessionAttribute LocalDateTime visitTime) {
        System.out.println(visitTime);

        // Load an event from DB
        Event event = new Event();
        event.setName("Writing");
        event.setLimit(30);

        /* Event newEvent = new Event();
        newEvent.setName(name);
        newEvent.setLimit(limit); */
        
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        eventList.add((Event) model.getAttribute("newEvent"));
        model.addAttribute("eventList", eventList);

        return "/events/list";
    }
}
