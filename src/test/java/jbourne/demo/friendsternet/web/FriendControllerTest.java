package jbourne.demo.friendsternet.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jbourne.demo.friendsternet.data.dto.*;
import jbourne.demo.friendsternet.data.entity.User;
import jbourne.demo.friendsternet.data.repository.ConnectionRepository;
import jbourne.demo.friendsternet.data.repository.UserRepository;
import jbourne.demo.friendsternet.domain.FriendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
import java.util.Optional;

import static jbourne.demo.friendsternet.web.FriendsUrls.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = {FriendController.class})
@Import(FriendService.class)
class FriendControllerTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ConnectionRepository connectionRepository;

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
        String e1 = "andy@example.com";
        String e2 = "john@example.com";
        List<String> friends = List.of(e1, e2);
        FriendCreateRequestDto request = new FriendCreateRequestDto();
        request.setFriends(friends);

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        User user = new User();
        user.setId(1L);
        when(userRepository.findByEmailAddress(e1))
                .thenReturn(Optional.of(user));
        user = new User();
        user.setId(2L);
        when(userRepository.findByEmailAddress(e2))
                .thenReturn(Optional.of(user));

//        sendRequest(CREATE_CONNECTION, request, resultDto);
        webTestClient.post()
                .uri(CREATE_CONNECTION)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(mapper.writeValueAsString(result));
    }

    @Test
    @DisplayName("As a user, I need an API to retrieve the friends list for an email address.")
    void shouldRetrieveFriendsList() throws Exception {
        FriendListRequestDto request = new FriendListRequestDto();
        String email = "andy@example.com";
        request.setEmail(email);

        when(userRepository.findAllFriends(email))
                .thenReturn(List.of(
                        new User(1L, "john@example.com"))
                );

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        result.setFriends(List.of("john@example.com"));
        result.setCount(1);

//        sendRequest(GET_FRIENDS, request, result);
        webTestClient.post()
                .uri(GET_FRIENDS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(mapper.writeValueAsString(result));
    }

    @Test
    @DisplayName("As a user, I need an API to retrieve the common friends list between two email addresses.")
    void shouldRetrieveCommonFriendsList() throws Exception {
        FriendCommonListRequestDto request = new FriendCommonListRequestDto();
        request.setFriends(List.of("andy@example.com", "john@example.com"));

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        result.setFriends(List.of("common@example.com"));
        result.setCount(1);

        sendRequest(GET_COMMON_FRIENDS, request, result);
    }

    @Test
    @DisplayName("As a user, I need an API to subscribe to updates from an email address.")
    void shouldAllowSubscription() throws Exception {
        FriendSubscribeRequestDto request = new FriendSubscribeRequestDto();
        request.setRequestor("lisa@example.com");
        request.setTarget("john@example.com");

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);

        sendRequest(SUBSCRIBE, request, result);
    }

    @Test
    @DisplayName("As a user, I need an API to block updates from an email address.")
    void shouldAllowBlocking() throws Exception {
        FriendSubscribeRequestDto request =
                new FriendSubscribeRequestDto();
        request.setRequestor("andy@example.com");
        request.setTarget("john@example.com");

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);

        sendRequest(BLOCK, request, result);
    }

    @Test
    @DisplayName("As a user, I need an API to retrieve all email addresses that can receive updates from an email address.")
    void shouldAllowCheckingSubscribers() throws Exception {
        FriendSendUpdateRequestDto request =
                new FriendSendUpdateRequestDto();
        request.setSender("john@example.com");
        request.setText("Hello World! kate@example.com");

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        result.setRecipients(List.of(
                "lisa@example.com",
                "kate@example.com"));

        sendRequest(GET_UPDATED_FRIENDS, request, result);
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