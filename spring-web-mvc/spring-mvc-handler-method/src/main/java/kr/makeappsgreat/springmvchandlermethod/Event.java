package kr.makeappsgreat.springmvchandlermethod;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Event {

    private Long id;

    @NotBlank()
    private String name;

    @Min(0)
    private Integer limit;

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
}
