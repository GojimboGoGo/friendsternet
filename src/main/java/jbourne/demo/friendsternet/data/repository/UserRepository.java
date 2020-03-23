package jbourne.demo.friendsternet.data.repository;

import jbourne.demo.friendsternet.data.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);

    @Query("SELECT u2 " +
            "FROM User u1 INNER JOIN Connection c ON u1.id = c.user1 " +
            "INNER JOIN User u2 ON c.user2 = u2.id " +
            "WHERE u1.emailAddress = ?1")
    List<User> findAllFriends(String emailAddress);
}
