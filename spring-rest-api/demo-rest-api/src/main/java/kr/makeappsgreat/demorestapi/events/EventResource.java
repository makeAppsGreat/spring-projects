package kr.makeappsgreat.demorestapi.events;

import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventResource extends EntityModel<Event> {

    public EventResource(Event event) {
        super(event);
        this.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}
