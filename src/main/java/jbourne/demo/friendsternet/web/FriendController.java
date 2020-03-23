package jbourne.demo.friendsternet.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jbourne.demo.friendsternet.data.dto.*;
import jbourne.demo.friendsternet.domain.FriendService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jbourne.demo.friendsternet.web.FriendsUrls.*;

@RestController
@Api
@RequestMapping(BASE_PATH)
public class FriendController {

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    private final FriendService friendService;

    @PostMapping(value = CREATE_CONNECTION)
    @ApiOperation("Create Connection")
    public FriendResultDto createConnection(
            @RequestBody FriendCreateRequestDto createRequestDto
    ) {
        return friendService.createFriendConnection(createRequestDto);
    }

    @PostMapping(value = GET_FRIENDS)
    @ApiOperation("Retrieve Friends List")
    public FriendResultDto retrieveFriends(
            @RequestBody FriendListRequestDto requestDto
    ) {
        return friendService.retrieveFriendsList(requestDto);
    }

    @PostMapping(value = GET_COMMON_FRIENDS)
    @ApiOperation("Retrieve Friends List in Common")
    public FriendResultDto retrieveCommonFriends(
            @RequestBody FriendCommonListRequestDto requestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }

    @PostMapping(value = SUBSCRIBE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Subscribe to Email")
    public FriendResultDto subscribeToEmail(
            @RequestBody FriendSubscribeRequestDto requestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }

    @PostMapping(value = BLOCK)
    @ApiOperation("Block updates from Email")
    public FriendResultDto blockEmail(
            @RequestBody FriendSubscribeRequestDto requestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }

    @PostMapping(value = GET_UPDATED_FRIENDS)
    @ApiOperation("Retrieve all Email Addresses to be Updated")
    public FriendResultDto getNotifiedFriends(
            @RequestBody FriendSendUpdateRequestDto requestDto
    ) {
        throw new IllegalStateException("TODO: implementation");
    }
}
