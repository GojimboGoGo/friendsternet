package jbourne.demo.friendsternet.data.repository;

import jbourne.demo.friendsternet.data.entity.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
