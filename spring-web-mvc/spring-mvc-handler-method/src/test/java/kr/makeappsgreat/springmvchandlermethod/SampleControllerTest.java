package kr.makeappsgreat.springmvchandlermethod;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getEvent() throws Exception {
        // mockMvc.perform(get("/events/1;name=Gayoun"))
        mockMvc.perform(get("/events/1").param("name", "Gayoun"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gayoun"));
    }

    @Test
    public void postEvnet() throws Exception {
        mockMvc.perform(post("/events")
                        .param("name", "Writing")
                        .param("limit", "-30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
    }
}