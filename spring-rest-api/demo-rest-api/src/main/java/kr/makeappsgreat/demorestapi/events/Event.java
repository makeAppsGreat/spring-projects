package kr.makeappsgreat.demorestapi.events;

import kr.makeappsgreat.demorestapi.accounts.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
public class Event {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    @Builder.Default
    private boolean offline = true;
    @Builder.Default
    private boolean free = false;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;
    @ManyToOne
    private Account manager;

    public void update() {
        // Update offline
        if (location == null || location.isBlank()) offline = false;

        // Update free
        if (basePrice == 0 && maxPrice == 0) free = true;
    }
}
