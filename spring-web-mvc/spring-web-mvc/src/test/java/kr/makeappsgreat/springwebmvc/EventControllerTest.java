package kr.makeappsgreat.springwebmvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void events() throws Exception{
        mockMvc.perform(get("/events"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("events"));
    }

    @Test
    public void eventsWithId() throws Exception{
        for (int i = 1; i < 4; i++) {
            mockMvc.perform(get("/events/" + i))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(String.valueOf(i)));
        }
    }

    @Test
    public void postEvents() throws Exception {
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("events"));
    }

    @Test
    public void deleteEvents() throws Exception {
        for (int i = 1; i < 4; i++) {
            mockMvc.perform(delete("/events/" + i))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

    @Test
    public void putEvents() throws Exception{
        for (int i = 1; i < 4; i++) {
            mockMvc.perform(put("/events/" + i)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }
}