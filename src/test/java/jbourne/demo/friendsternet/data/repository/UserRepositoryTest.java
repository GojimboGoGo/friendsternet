package jbourne.demo.friendsternet.data.repository;

import jbourne.demo.friendsternet.data.entity.Connection;
import jbourne.demo.friendsternet.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    private User user1 = new User(1L, "andy@example.com");
    private User user2 = new User(2L, "john@example.com");
    private User user3 = new User(3L, "alice@example.com");
    private Connection friendConnection = new Connection(1L, user1.getId(), user2.getId());
    private Connection friendConnection2 = new Connection(2L, user1.getId(), user3.getId());
    private Connection friendConnection3 = new Connection(3L, user2.getId(), user3.getId());

    @BeforeEach
    void setup() {
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        connectionRepository.save(friendConnection);
        connectionRepository.save(friendConnection2);
        connectionRepository.save(friendConnection3);
    }

    @Test
    void findByEmailAddress() {
        Optional<User> user = userRepository.findByEmailAddress("andy@example.com");

        assertEquals(user1, user.get());
    }

    @Test
    void findAllFriends() {
        List<User> expected = List.of(user2, user3);

        List<User> result = userRepository.findAllFriends("andy@example.com");

        assertEquals(expected, result);
    }

    @Test
    void findAllCommonFriends() {
        List<User> expected = List.of(user3);

        List<User> result = userRepository.findAllCommonFriends("andy@example.com", "john@example.com");

        assertEquals(expected, result);
    }
}