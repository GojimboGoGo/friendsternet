package jbourne.demo.friendsternet.data.repository;

import jbourne.demo.friendsternet.data.entity.Blocklist;
import org.springframework.data.repository.CrudRepository;

public interface BlocklistRepository extends CrudRepository<Blocklist, Long> {
}
