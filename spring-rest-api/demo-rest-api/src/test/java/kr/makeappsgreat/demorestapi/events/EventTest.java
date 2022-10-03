package kr.makeappsgreat.demorestapi.events;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with Spring")
                .build();

        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        // Given
        String name = "New Event";
        String description = "Spring";

        // When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        // Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @ParameterizedTest
    @MethodSource("testFreeProvider")
    public void testFree(int basePrice, int maxPrice, boolean result) {
        // Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isFree()).isEqualTo(result);
    }

    static Stream<Arguments> testFreeProvider() {
        return Stream.of(
                Arguments.arguments(0, 0, true),
                Arguments.arguments(100, 0, false),
                Arguments.arguments(0, 100, false)
        );
    }


    @ParameterizedTest
    @MethodSource("testOfflineProvider")
    public void testOffline(String location, boolean result) {
        // Given
        Event event = Event.builder()
                .location(location)
                .build();

        // When
        event.update();

        // Then
        assertThat(event.isOffline()).isEqualTo(result);
    }

    static Stream<Arguments> testOfflineProvider() {
        return Stream.of(
                Arguments.arguments("Some Location...", true),
                Arguments.arguments(null, false),
                Arguments.arguments("", false),
                Arguments.arguments(" ", false)
        );
    }
}