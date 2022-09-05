package kr.makeappsgreat.springwebmvc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private MockMvc mockMvc;

    @Test
    public void helloWithName() throws Exception {
        String uri = "/hello/Gayoun";

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Gayoun!"));

        mockMvc.perform(post(uri))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(put(uri))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(get(uri + ".json"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
                .andExpect(handler().handlerType(SampleController.class))
                .andExpect(handler().methodName("hello"));
    }

    @Test
    public void helloWithHeader() throws Exception {
        mockMvc.perform(get("/hello")
                        .header(HttpHeaders.FROM, "localhost"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
                .andExpect(handler().handlerType(SampleController.class))
                .andExpect(handler().methodName("helloWithHeader"));
    }

    @Test
    public void helloWithParam() throws Exception {
        mockMvc.perform(get("/hello")
                        .param("name", "makeappsgreat"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, makeappsgreat!"))
                .andExpect(handler().handlerType(SampleController.class))
                .andExpect(handler().methodName("helloWithParam"));
    }

    @Test
    public void testMethod() throws Exception {
        mockMvc.perform(head("/hello")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(SampleController.class))
                .andExpect(handler().methodName("hello"));

        mockMvc.perform(options("/hello")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.ALLOW));
    }
}