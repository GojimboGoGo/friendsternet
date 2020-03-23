package jbourne.demo.friendsternet.data.repository;

import jbourne.demo.friendsternet.data.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);

    @Query("SELECT u2 " +
            "FROM User u1 INNER JOIN Connection c ON u1.id = c.user1 " +
            "INNER JOIN User u2 ON c.user2 = u2.id " +
            "WHERE u1.emailAddress = ?1")
    List<User> findAllFriends(String emailAddress);

    @Query("SELECT u2 " +
            "FROM User u1 INNER JOIN Connection c ON u1.id = c.user1 " +
            "INNER JOIN User u2 ON c.user2 = u2.id " +
            "WHERE u1.emailAddress = ?1 OR u1.emailAddress = ?2 " +
            "GROUP BY c.user2 " +
            "HAVING COUNT(c.user1) > 1")
    List<User> findAllCommonFriends(String emailAddress1, String emailAddress2);

    @Query("SELECT sub " +
            "FROM User sub INNER JOIN Subscription s ON sub.id = s.subscriber " +
            "INNER JOIN User target ON target.id = s.target " +
            "WHERE target.emailAddress = ?1 ")
    Collection<User> findAllSubscribers(String email);

    @Query("SELECT blocker " +
            "FROM User blocker INNER JOIN Blocklist b ON blocker.id = b.blocker " +
            "INNER JOIN User target ON target.id = b.target " +
            "WHERE target.emailAddress = ?1")
    List<User> findAllWhoHaveBlocked(String email);
}
