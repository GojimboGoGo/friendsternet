package jbourne.demo.friendsternet.web;

import jbourne.demo.friendsternet.data.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jbourne.demo.friendsternet.web.FriendsUrls.*;

@RestController
@RequestMapping(BASE_PATH)
public class FriendController {

    @PostMapping(value = CREATE_CONNECTION)
    public FriendResultDto createConnection(
            @RequestBody FriendCreateRequestDto createRequestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }

    @PostMapping(value = GET_FRIENDS)
    public FriendResultDto retrieveFriends(
            @RequestBody FriendListRequestDto createRequestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }

    @PostMapping(value = GET_COMMON_FRIENDS)
    public FriendResultDto retrieveCommonFriends(
            @RequestBody FriendCommonListRequestDto createRequestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }

    @PostMapping(value = SUBSCRIBE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendResultDto subscribeToEmail(
            @RequestBody FriendSubscribeRequestDto createRequestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }

    @PostMapping(value = BLOCK)
    public FriendResultDto blockEmail(
            @RequestBody FriendSubscribeRequestDto createRequestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }

    @PostMapping(value = GET_UPDATED_FRIENDS)
    public FriendResultDto getNotifiedFriends(
            @RequestBody FriendSendUpdateRequestDto createRequestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }
}
