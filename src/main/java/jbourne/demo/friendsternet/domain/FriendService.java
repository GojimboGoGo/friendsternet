package jbourne.demo.friendsternet.domain;

import jbourne.demo.friendsternet.data.dto.FriendCreateRequestDto;
import jbourne.demo.friendsternet.data.dto.FriendResultDto;
import jbourne.demo.friendsternet.data.entity.Connection;
import jbourne.demo.friendsternet.data.entity.User;
import jbourne.demo.friendsternet.data.repository.ConnectionRepository;
import jbourne.demo.friendsternet.data.repository.UserRepository;
import jbourne.demo.friendsternet.exception.BadRequestException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    public FriendService(UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

    private final UserRepository userRepository;
    private final ConnectionRepository connectionRepository;

    public FriendResultDto createFriendConnection(FriendCreateRequestDto requestDto) {
        if (requestDto.getFriends().size() != 2) {
            throw new BadRequestException("Invalid connection request! Too many friends.");
        }
        if (requestDto.getFriends().stream()
                .anyMatch(email -> !EmailValidator.getInstance().isValid(email))) {
            throw new BadRequestException("Invalid user email");
        }

        User user1 = userRepository.findByEmailAddress(requestDto.getFriends().get(0)).orElseThrow();
        User user2 = userRepository.findByEmailAddress(requestDto.getFriends().get(1)).orElseThrow();

        Connection toSave = new Connection();
        toSave.setUser1(user1.getId());
        toSave.setUser2(user2.getId());
        connectionRepository.save(toSave);

        FriendResultDto result = new FriendResultDto();
        result.setSuccess(true);
        return result;
    }
}
