package core.matchingcomponent.repository;

import core.matchingcomponent.model.UsersDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersDB, Long> {
}
