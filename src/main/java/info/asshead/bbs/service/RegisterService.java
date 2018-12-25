package info.asshead.bbs.service;

import com.google.common.hash.Hashing;
import info.asshead.bbs.entity.Room;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.repository.RoomRepository;
import info.asshead.bbs.repository.UserRepository;
import info.asshead.bbs.util.SplitRoomStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author jason
 */
@Service
@Slf4j
public class RegisterService {

  public User register(String username, String password, String roomString) throws Exception {
    Room room = SplitRoomStringUtil.split(roomString);

    Optional<Room> roomOptional = roomRepository.findByBuildAndAndUnitAndRoom(room.getBuild(), room.getUnit(), room.getRoom());
    if (roomOptional.isPresent()) {
      Optional<User> userOptional = userRepository.findByRoomId(roomOptional.get().getId());
      if (!userOptional.isPresent()) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
        user.setRoomId(roomOptional.get().getId());
        userRepository.save(user);
        log.info("{}:{} register success!", roomString, username);
        return user;
      }else{
        log.info("{} was registered!", roomString);
        return null;
      }
    }
    log.info("{} not exists!", roomString);
    return null;
  }

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private UserRepository userRepository;
}
