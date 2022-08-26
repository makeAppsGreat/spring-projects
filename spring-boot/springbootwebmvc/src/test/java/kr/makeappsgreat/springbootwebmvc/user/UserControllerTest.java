package kr.makeappsgreat.springbootwebmvc.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    /* @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect((content().string("hello")));
    } */

    @Test
    public void createUserToJson() throws Exception {
        String userJson = "{\"username\":\"Gayoun\", \"email\":\"makeappsgreat@gmail.com\"}";

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(equalTo("Gayoun"))))
                .andExpect(jsonPath("$.email", is(equalTo("makeappsgreat@gmail.com"))));
    }

    @Test
    public void createUserToXML() throws Exception {
        String userJson = "{\"username\":\"Gayoun\", \"email\":\"makeappsgreat@gmail.com\"}";

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_XML)
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/User/username").string("Gayoun"))
                .andExpect(xpath("/User/email").string("makeappsgreat@gmail.com"));
    }
}
