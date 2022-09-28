package kr.makeappsgreat.demorestapi.events;


import com.fasterxml.jackson.databind.ObjectMapper;
import kr.makeappsgreat.demorestapi.common.RestDocsConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class EventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    class CreateEvent {

        @Test
        @DisplayName("정상 요청")
        public void createEvent() throws Exception {
            EventDto event = EventDto.builder()
                    .name("Spring")
                    .description("REST API Development with Spring")
                    .beginEnrollmentDateTime(LocalDateTime.of(2022, 8, 15, 0, 0))
                    .closeEnrollmentDateTime(LocalDateTime.of(2022, 9, 1, 0, 0))
                    .beginEventDateTime(LocalDateTime.of(2022, 9, 24, 14, 0))
                    .endEventDateTime(LocalDateTime.of(2022, 9, 25, 16, 0))
                    .basePrice(100)
                    .maxPrice(200)
                    .limitOfEnrollment(50)
                    .location("A302 강의실")
                    .build();

            mockMvc.perform(post("/api/events/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(event)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(header().exists(HttpHeaders.LOCATION))
                    .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                    .andExpect(jsonPath("free").value(false))
                    .andExpect(jsonPath("offline").value(true))
                    .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.query-events").exists())
                    .andExpect(jsonPath("_links.update-event").exists())
                    .andDo(document("create-event",
                            links(
                                    linkWithRel("self").description("link to self"),
                                    linkWithRel("query-events").description("link to query events"),
                                    linkWithRel("update-event").description("link to update an existing event"),
                                    linkWithRel("profile").description("link to profile")
                            ),
                            requestHeaders(
                                    headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                            ),
                            requestFields(
                                    fieldWithPath("name").description("name of new event"),
                                    fieldWithPath("description").description("description of new event"),
                                    fieldWithPath("beginEnrollmentDateTime").description("date time of begin of enrollment"),
                                    fieldWithPath("closeEnrollmentDateTime").description("date time of close of enrollment"),
                                    fieldWithPath("beginEventDateTime").description("date time of begin of event"),
                                    fieldWithPath("endEventDateTime").description("date time of end of event"),
                                    fieldWithPath("location").description("location of new event"),
                                    fieldWithPath("basePrice").description("base price of new event"),
                                    fieldWithPath("maxPrice").description("max price of new event"),
                                    fieldWithPath("limitOfEnrollment").description("limit of enrollment")
                            ),
                            responseHeaders(
                                    headerWithName(HttpHeaders.LOCATION).description("location header"),
                                    headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                            ),
                            relaxedResponseFields(
                                    fieldWithPath("name").description("name of new event"),
                                    fieldWithPath("description").description("description of new event"),
                                    fieldWithPath("beginEnrollmentDateTime").description("date time of begin of enrollment"),
                                    fieldWithPath("closeEnrollmentDateTime").description("date time of close of enrollment"),
                                    fieldWithPath("beginEventDateTime").description("date time of begin of event"),
                                    fieldWithPath("endEventDateTime").description("date time of end of event"),
                                    fieldWithPath("location").description("location of new event"),
                                    fieldWithPath("basePrice").description("base price of new event"),
                                    fieldWithPath("maxPrice").description("max price of new event"),
                                    fieldWithPath("limitOfEnrollment").description("limit of enrollment"),
                                    fieldWithPath("free").description("It tells if this event is free or not."),
                                    fieldWithPath("offline").description("It tells if this event is offline event or not."),
                                    fieldWithPath("eventStatus").description("status of event"),

                                    fieldWithPath("_links.self.href").description("link to sef"),
                                    fieldWithPath("_links.query-events.href").description("link to query event list"),
                                    fieldWithPath("_links.update-event.href").description("link to update existing event"),
                                    fieldWithPath("_links.profile.href").description("link to profile")
                            )
                    ));
        }

        @Test
        @DisplayName("기대하지 않은 입력이 포함된 요청")
        public void createEventWithBadRequest() throws Exception {
            boolean free = true;
            boolean offline = false;
            Long id = 100L;

            Event event = Event.builder()
                    .name("Spring")
                    .description("REST API Development with Spring")
                    .beginEnrollmentDateTime(LocalDateTime.of(2022, 8, 15, 0, 0))
                    .closeEnrollmentDateTime(LocalDateTime.of(2022, 9, 1, 0, 0))
                    .beginEventDateTime(LocalDateTime.of(2022, 9, 24, 14, 0))
                    .endEventDateTime(LocalDateTime.of(2022, 9, 25, 16, 0))
                    .basePrice(100)
                    .maxPrice(200)
                    .limitOfEnrollment(50)
                    .location("A302 강의실")
                    .free(free)
                    .offline(offline)
                    .build();
            event.setId(id);

            mockMvc.perform(post("/api/events/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(event)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("비어 있는 입력 요청")
        public void createEventWithEmptyInput() throws Exception {
            EventDto eventDto = EventDto.builder().build();

            mockMvc.perform(post("/api/events")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("잘못된 입력이 포함된 요청")
        public void createEventWithWrongInput() throws Exception {
            EventDto eventDto = EventDto.builder()
                    .name("Spring")
                    .description("REST API Development with Spring")
                    .beginEnrollmentDateTime(LocalDateTime.of(2022, 8, 15, 0, 0))
                    .closeEnrollmentDateTime(LocalDateTime.of(2022, 9, 1, 0, 0))
                    .beginEventDateTime(LocalDateTime.of(2022, 9, 24, 14, 0))
                    .endEventDateTime(LocalDateTime.of(2022, 9, 23, 16, 0))
                    .basePrice(1000)
                    .maxPrice(200)
                    .limitOfEnrollment(50)
                    .location("A302 강의실")
                    .build();

            mockMvc.perform(post("/api/events")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("errors[0].objectName").exists())
                    .andExpect(jsonPath("errors[0].defaultMessage").exists())
                    .andExpect(jsonPath("errors[0].code").exists())
                    .andExpect(jsonPath("_links.index").exists());
        }
    }
}
