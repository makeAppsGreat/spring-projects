package kr.makeappsgreat.demorestapi.events;

import kr.makeappsgreat.demorestapi.accounts.Account;
import kr.makeappsgreat.demorestapi.accounts.CurrentUser;
import kr.makeappsgreat.demorestapi.common.ErrorsResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;

    private final EventValidator eventValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public EventController(EventRepository eventRepository, EventValidator eventValidator, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.eventValidator = eventValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors,
                                      @CurrentUser Account currentUser) {
        if (errors.hasErrors()) return badRequest(errors);

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) return badRequest(errors);

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        event.setManager(currentUser);
        Event savedEvent = eventRepository.save(event);

        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(savedEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();

        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        eventResource.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));


        return ResponseEntity.created(createdUri).body(eventResource);
    }

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable,
                                      PagedResourcesAssembler<Event> assembler,
                                      @CurrentUser Account currentUser) {
        Page<Event> all = eventRepository.findAll(pageable);
        PagedModel<EntityModel<Event>> pagedModel = assembler.toModel(all, e -> new EventResource(e));
        pagedModel.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
        if (currentUser != null) pagedModel.add(linkTo((EventController.class)).withRel("create-event"));

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Long id,
                                   @CurrentUser Account currentUser) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) return ResponseEntity.notFound().build();

        Event event = optionalEvent.get();
        EventResource eventResource = new EventResource(event);
        eventResource.add(Link.of("/docs/index.html#resources-events-get").withRel("profile"));
        if (event.getManager().equals(currentUser))
            eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));

        return ResponseEntity.ok(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Long id,
                                      @RequestBody @Valid EventDto eventDto, Errors errors,
                                      @CurrentUser Account currentUser) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) return ResponseEntity.notFound().build();

        if (errors.hasErrors()) return badRequest(errors);

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) return badRequest(errors);

        Event existingEvent = optionalEvent.get();
        if (!existingEvent.getManager().equals(currentUser)) return new ResponseEntity(HttpStatus.UNAUTHORIZED);


        modelMapper.map(eventDto, existingEvent);

        Event savedEvent = eventRepository.save(existingEvent);
        EventResource eventResource = new EventResource(savedEvent);
        eventResource.add(Link.of("/docs/index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok().body(eventResource);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
