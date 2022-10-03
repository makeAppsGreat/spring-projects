package kr.makeappsgreat.demorestapi.events;


import kr.makeappsgreat.demorestapi.accounts.Account;
import kr.makeappsgreat.demorestapi.accounts.AccountRepository;
import kr.makeappsgreat.demorestapi.accounts.AccountRole;
import kr.makeappsgreat.demorestapi.accounts.AccountService;
import kr.makeappsgreat.demorestapi.common.BaseControllerTest;
import kr.makeappsgreat.demorestapi.config.AppProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerTest extends BaseControllerTest {

    // @TODO : Add document details

    @Autowired
    EventRepository eventRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    AppProperties properties;

    @BeforeEach
    public void setup() {
        eventRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Nested
    @DisplayName("이벤트 생성하기")
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
                            .header(HttpHeaders.AUTHORIZATION, getBearerToken())
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
                            .header(HttpHeaders.AUTHORIZATION, getBearerToken())
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
                            .header(HttpHeaders.AUTHORIZATION, getBearerToken())
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
                            .header(HttpHeaders.AUTHORIZATION, getBearerToken())
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

    @Test
    @DisplayName("이벤트 목록 조회하기 / Event : 30, Page : 2, Events per Page : 10")
    public void queryEvents() throws Exception {
        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // When & Then
        mockMvc.perform(get("/api/events")
                        .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.create-event").exists())
                .andDo(document("query-events"));
    }

    @Nested
    @DisplayName("이벤트 조회하기")
    class GetEvent {

        @Test
        @DisplayName("정상 요청")
        public void getEvent() throws Exception {
            // Given
            Event event = generateEvent(100);

            // When & Then
            mockMvc.perform(get("/api/events/{id}", event.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("name").exists())
                    .andExpect(jsonPath("id").exists())
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.profile").exists())
                    .andDo(document("get-an-event"));
        }

        @Test
        @DisplayName("등록되지 않은 이벤트 조회")
        public void requestBadEvent() throws Exception {
            // When & Then
            mockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("이벤트 수정하기")
    class UpdateEvent {

        @Test
        @DisplayName("정상 요청")
        public void updateEvent() throws Exception {
            // Given
            Event event = generateEvent(200);
            String descriptionToEdit = "수정된 이벤트 설명";

            EventDto eventDto = modelMapper.map(event, EventDto.class);
            eventDto.setDescription(descriptionToEdit);

            // When & Then
            mockMvc.perform(put("/api/events/{id}", event.getId())
                            .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("description").value(descriptionToEdit))
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.profile").exists())
                    .andDo(document("update-event"));
        }

        @Test
        @DisplayName("비어 있는 입력 요청")
        public void updateWithEmptyEvent() throws Exception {
            // Given
            Event event = generateEvent(201);
            EventDto eventDto = new EventDto();

            // When & Then
            mockMvc.perform(put("/api/events/{id}", event.getId())
                            .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("잘못된 입력이 포함된 요청")
        public void updateWithBadEvent() throws Exception {
            // Given
            Event event = generateEvent(203);
            EventDto eventDto = modelMapper.map(event, EventDto.class);
            eventDto.setBasePrice(2000);
            eventDto.setMaxPrice(1000);

            // When & Then
            mockMvc.perform(put("/api/events/{id}", event.getId())
                            .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("등록되지 않은 이벤트 조회")
        public void updateWithNotExistEvent() throws Exception {
            // Given
            EventDto eventDto = EventDto.builder()
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

            // When & Then
            mockMvc.perform(put("/api/events/{id}", Long.MAX_VALUE)
                            .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    private Event generateEvent(int index) {
        Event event = Event.builder()
                .name("Event " + index)
                .description("Test Event")
                .beginEnrollmentDateTime(LocalDateTime.of(2022, 8, 15, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2022, 9, 1, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2022, 9, 24, 14, 0))
                .endEventDateTime(LocalDateTime.of(2022, 9, 25, 16, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(50)
                .location("A302 강의실")
                .free(false)
                .offline(true)
                .eventStatus(EventStatus.DRAFT)
                .build();

        return eventRepository.save(event);
    }

    private String getBearerToken() throws Exception {
        String email = "event-controller@makeappsgreat.kr";
        String password = "simple";
        String clientId = properties.getClientId();
        String clientSecret = properties.getClientSecret();

        Account account = Account.builder()
                .email(email)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        accountService.saveAccount(account);


        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", email)
                .param("password", password)
                .param("grant_type", "password"));

        String responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return "Bearer " + parser.parseMap(responseBody).get("access_token").toString();
    }
}
