package kr.makeappsgreat.springmvchandlermethod;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EventValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Event.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Event event = (Event) target;
        if (event.getName().equalsIgnoreCase("Hello")) {
            errors.rejectValue("name", "wrongValue", "The value is not allowed.");
        }
    }
}
