package kr.makeappsgreat.springmvchandlermethod;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class Event {

    private Long id;

    @NotBlank()
    private String name;

    @Min(0)
    private Integer limit;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate start;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        System.out.println(start);

        this.start = start;
    }
}
