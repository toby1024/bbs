package info.asshead.bbs.repository;

import info.asshead.bbs.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
  Optional<User> findByUsername(String username);

  Optional<User> findByRoomId(int roomId);

}
