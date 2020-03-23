package jbourne.demo.friendsternet.domain;

import jbourne.demo.friendsternet.data.dto.*;
import jbourne.demo.friendsternet.data.entity.Blocklist;
import jbourne.demo.friendsternet.data.entity.Connection;
import jbourne.demo.friendsternet.data.entity.Subscription;
import jbourne.demo.friendsternet.data.entity.User;
import jbourne.demo.friendsternet.data.repository.BlocklistRepository;
import jbourne.demo.friendsternet.data.repository.ConnectionRepository;
import jbourne.demo.friendsternet.data.repository.SubscriptionRepository;
import jbourne.demo.friendsternet.data.repository.UserRepository;
import jbourne.demo.friendsternet.exception.BadRequestException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendService {

    public static final String INVALID_USER_EMAIL = "Invalid user email";

    public FriendService(UserRepository userRepository, ConnectionRepository connectionRepository, SubscriptionRepository subscriptionRepository, BlocklistRepository blocklistRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.blocklistRepository = blocklistRepository;
    }

    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BlocklistRepository blocklistRepository;

    public FriendResultDto createFriendConnection(FriendCreateRequestDto requestDto) {
        if (requestDto.getFriends().size() != 2) {
            throw new BadRequestException("Invalid connection request! Please enter 2 email addresses only.");
        }
        if (requestDto.getFriends().stream()
                .anyMatch(email -> !EmailValidator.getInstance().isValid(email))) {
            throw new BadRequestException(INVALID_USER_EMAIL);
        }

        var user1 = userRepository.findByEmailAddress(requestDto.getFriends().get(0)).orElseThrow();
        var user2 = userRepository.findByEmailAddress(requestDto.getFriends().get(1)).orElseThrow();

        Connection toSave = new Connection();
        toSave.setUser1(user1.getId());
        toSave.setUser2(user2.getId());
        connectionRepository.save(toSave);
        toSave = new Connection();
        toSave.setUser2(user1.getId());
        toSave.setUser1(user2.getId());
        connectionRepository.save(toSave);

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        return result;
    }

    public FriendResultDto retrieveFriendsList(FriendListRequestDto requestDto) {
        if (!EmailValidator.getInstance().isValid(requestDto.getEmail())) {
            throw new BadRequestException(INVALID_USER_EMAIL);
        }

        List<String> friends =
                userRepository.findAllFriends(requestDto.getEmail()).stream()
                        .map(User::getEmailAddress)
                        .collect(Collectors.toList());

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        result.setFriends(friends);
        result.setCount(friends.size());
        return result;
    }


    public FriendResultDto retrieveCommonFriendsLists(FriendCommonListRequestDto requestDto) {
        if (requestDto.getFriends().size() != 2) {
            throw new BadRequestException("Invalid connection request! Please enter 2 email addresses only.");
        }
        if (requestDto.getFriends().stream()
                .anyMatch(email -> !EmailValidator.getInstance().isValid(email))) {
            throw new BadRequestException(INVALID_USER_EMAIL);
        }

        List<String> friends = requestDto.getFriends();
        List<String> commonFriends = userRepository.findAllCommonFriends(friends.get(0), friends.get(1)).stream()
                .map(User::getEmailAddress)
                .collect(Collectors.toList());

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        result.setFriends(commonFriends);
        result.setCount(commonFriends.size());
        return result;
    }

    public FriendResultDto subscribeToUser(FriendSubscribeRequestDto requestDto) {
        if (!EmailValidator.getInstance().isValid(requestDto.getRequestor())) {
            throw new BadRequestException(INVALID_USER_EMAIL);
        }
        if (!EmailValidator.getInstance().isValid(requestDto.getTarget())) {
            throw new BadRequestException(INVALID_USER_EMAIL);
        }

        var subscriber = userRepository.findByEmailAddress(requestDto.getRequestor()).orElseThrow();
        var target = userRepository.findByEmailAddress(requestDto.getTarget()).orElseThrow();
        var subscription = new Subscription();
        subscription.setSubscriber(subscriber.getId());
        subscription.setTarget(target.getId());
        subscriptionRepository.save(subscription);

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        return result;
    }

    public FriendResultDto blockUser(FriendSubscribeRequestDto requestDto) {
        if (!EmailValidator.getInstance().isValid(requestDto.getRequestor())) {
            throw new BadRequestException(INVALID_USER_EMAIL);
        }
        if (!EmailValidator.getInstance().isValid(requestDto.getTarget())) {
            throw new BadRequestException(INVALID_USER_EMAIL);
        }

        var blocker = userRepository.findByEmailAddress(requestDto.getRequestor()).orElseThrow();
        var target = userRepository.findByEmailAddress(requestDto.getTarget()).orElseThrow();
        var subscription = new Blocklist();
        subscription.setBlocker(blocker.getId());
        subscription.setTarget(target.getId());
        blocklistRepository.save(subscription);

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        return result;
    }
}
