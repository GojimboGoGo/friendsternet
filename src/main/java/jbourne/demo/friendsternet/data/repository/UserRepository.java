package jbourne.demo.friendsternet.data.repository;

import jbourne.demo.friendsternet.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);
}
