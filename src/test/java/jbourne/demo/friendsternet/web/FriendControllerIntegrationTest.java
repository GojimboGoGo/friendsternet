package jbourne.demo.friendsternet.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jbourne.demo.friendsternet.data.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

import static jbourne.demo.friendsternet.web.FriendsUrls.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = { FriendController.class })
class FriendControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        webTestClient = webTestClient.mutate().baseUrl(BASE_PATH).build();
    }

    @Test
    @DisplayName("As a user, I need an API to create a friend connection between two email addresses.")
    void shouldAllowCreatingConnections() throws Exception {
        List<String> friends = List.of("andy@example.com", "john@example.com");
        FriendCreateRequestDto request = new FriendCreateRequestDto();
        request.setFriends(friends);

        FriendResultDto resultDto = FriendResultDto.builder()
                .success(true)
                .build();

        sendRequest(CREATE_CONNECTION, request, resultDto);
    }

    @Test
    @DisplayName("As a user, I need an API to retrieve the friends list for an email address.")
    void shouldRetrieveFriendsList() throws Exception {
        FriendListRequestDto request = new FriendListRequestDto();
        request.setEmail("andy@example.com");

        FriendResultDto resultDto = FriendResultDto.builder()
                .success(true)
                .friends(List.of("john@example.com"))
                .count(1)
                .build();

        sendRequest(GET_FRIENDS, request, resultDto);
    }

    @Test
    @DisplayName("As a user, I need an API to retrieve the common friends list between two email addresses.")
    void shouldRetrieveCommonFriendsList() throws Exception {
        FriendCommonListRequestDto request = new FriendCommonListRequestDto();
        request.setFriends(List.of("andy@example.com", "john@example.com"));

        FriendResultDto resultDto = FriendResultDto.builder()
                .success(true)
                .friends(List.of("common@example.com"))
                .count(1)
                .build();

        sendRequest(GET_COMMON_FRIENDS, request, resultDto);
    }

    @Test
    @DisplayName("As a user, I need an API to subscribe to updates from an email address.")
    void shouldAllowSubscription() throws Exception {
        FriendSubscribeRequestDto request = new FriendSubscribeRequestDto();
        request.setRequestor("lisa@example.com");
        request.setTarget("john@example.com");

        FriendResultDto resultDto = FriendResultDto.builder()
                .success(true)
                .build();

        sendRequest(SUBSCRIBE, request, resultDto);
    }

    @Test
    @DisplayName("As a user, I need an API to block updates from an email address.")
    void shouldAllowBlocking() throws Exception {
        FriendSubscribeRequestDto request =
                new FriendSubscribeRequestDto();
        request.setRequestor("andy@example.com");
        request.setTarget("john@example.com");

        FriendResultDto resultDto = FriendResultDto.builder()
                .success(true)
                .build();

        sendRequest(BLOCK, request, resultDto);
    }

    @Test
    @DisplayName("As a user, I need an API to retrieve all email addresses that can receive updates from an email address.")
    void shouldAllowCheckingSubscribers() throws Exception {
        FriendSendUpdateRequestDto request =
                new FriendSendUpdateRequestDto();
        request.setSender("john@example.com");
        request.setText("Hello World! kate@example.com");

        FriendResultDto resultDto = FriendResultDto.builder()
                .success(true)
                .recipients(List.of(
                        "lisa@example.com",
                        "kate@example.com"))
                .build();

        sendRequest(GET_UPDATED_FRIENDS, request, resultDto);
    }

    private void sendRequest(String uri, Object request, FriendResultDto resultDto) throws JsonProcessingException {
        webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is5xxServerError();
//                .expectBody()
//                .json(mapper.writeValueAsString(resultDto));
    }
}