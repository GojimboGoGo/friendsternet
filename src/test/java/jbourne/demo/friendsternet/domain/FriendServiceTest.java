package jbourne.demo.friendsternet.domain;

import jbourne.demo.friendsternet.data.dto.FriendCreateRequestDto;
import jbourne.demo.friendsternet.data.dto.FriendListRequestDto;
import jbourne.demo.friendsternet.data.dto.FriendResultDto;
import jbourne.demo.friendsternet.data.entity.User;
import jbourne.demo.friendsternet.data.repository.ConnectionRepository;
import jbourne.demo.friendsternet.data.repository.UserRepository;
import jbourne.demo.friendsternet.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        FriendService.class
})
class FriendServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ConnectionRepository connectionRepository;

    @Autowired
    FriendServiceTest(FriendService friendService) {
        this.friendService = friendService;
    }

    private final FriendService friendService;

    @Test
    void createFriendShouldCreateConnection() {
        FriendCreateRequestDto requestDto = new FriendCreateRequestDto();
        String user1EmailAddress = "andy@example.com";
        String user2EmailAddress = "john@example.com";
        User user1 = new User();
        user1.setId(0L);
        user1.setEmailAddress(user1EmailAddress);
        User user2 = new User();
        user2.setId(1L);
        user2.setEmailAddress(user2EmailAddress);

        requestDto.setFriends(List.of(user1EmailAddress, user2EmailAddress));

        when(userRepository.findByEmailAddress(user1EmailAddress))
                .thenReturn(Optional.of(user1));
        when(userRepository.findByEmailAddress(user2EmailAddress))
                .thenReturn(Optional.of(user2));

        FriendResultDto resultDto = friendService.createFriendConnection(requestDto);

        assertTrue(resultDto.getSuccess());
    }

    @Test
    void createFriendShouldThrowErrorForInvalidParameterSize() {
        FriendCreateRequestDto tooShortRequest = new FriendCreateRequestDto();
        tooShortRequest.setFriends(List.of("andy@example.com"));
        FriendCreateRequestDto tooLongRequest = new FriendCreateRequestDto();
        tooLongRequest.setFriends(List.of("andy@example.com", "john@example.com", "zoidberg@example.com"));

        assertThrows(Exception.class, () -> friendService.createFriendConnection(tooShortRequest));
        assertThrows(Exception.class, () -> friendService.createFriendConnection(tooLongRequest));
    }

    @Test
    void createFriendShouldThrowErrorForInvalidFriendEmail() {
        FriendCreateRequestDto nonEmailRequest = new FriendCreateRequestDto();
        nonEmailRequest.setFriends(List.of("andy", "john@example.com"));

        assertThrows(Exception.class, () -> friendService.createFriendConnection(nonEmailRequest));
    }

    @Test
    void createFriendShouldThrowErrorIfNoUserFound() {
        String invalidEmailAddress = "nobody@example.com";
        String otherAddress = "andy@example.com";
        when(userRepository.findByEmailAddress(invalidEmailAddress))
                .thenReturn(Optional.empty());

        FriendCreateRequestDto requestDto1 = new FriendCreateRequestDto();
        requestDto1.setFriends(List.of(invalidEmailAddress, otherAddress));

        FriendCreateRequestDto requestDto2 = new FriendCreateRequestDto();
        requestDto1.setFriends(List.of(otherAddress, invalidEmailAddress));

        assertThrows(Exception.class, () -> friendService.createFriendConnection(requestDto1));
        assertThrows(Exception.class, () -> friendService.createFriendConnection(requestDto2));
    }

    @Test
    void friendsListShouldGetValidUser() {
        String emailAddress = "andy@example.com";
        User user2 = new User();
        user2.setId(2L);
        user2.setEmailAddress("john@example.com");
        when(userRepository.findAllFriends(emailAddress))
                .thenReturn(List.of(user2));

        FriendListRequestDto requestDto = new FriendListRequestDto();
        requestDto.setEmail(emailAddress);

        FriendResultDto result = friendService.retrieveFriendsList(requestDto);

        assertTrue(result.getSuccess());
        assertEquals(List.of(user2.getEmailAddress()), result.getFriends());
        assertEquals(1, result.getCount());
    }

    @Test
    void friendsListShouldReturnErrorForInvalidEmail() {
        String emailAddress = "aaa@example..com";

        FriendListRequestDto requestDto = new FriendListRequestDto();
        requestDto.setEmail(emailAddress);

        assertThrows(BadRequestException.class, () -> friendService.retrieveFriendsList(requestDto));
    }
}