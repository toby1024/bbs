package info.asshead.bbs.repository;

import info.asshead.bbs.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author jason
 */
public interface RoomRepository extends CrudRepository<Room, Integer> {

  Optional<Room> findByBuildAndUnitAndRoom(int build, int unit, int room);
}
