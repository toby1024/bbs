package info.asshead.bbs.service;

import com.google.common.hash.Hashing;
import info.asshead.bbs.entity.Room;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.exception.RoomStringParseException;
import info.asshead.bbs.repository.RoomRepository;
import info.asshead.bbs.repository.UserRepository;
import info.asshead.bbs.util.SplitRoomStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

/**
 * @author jason
 */
@Service
@Slf4j
public class RegisterService {

  public User register(String username, String password, String roomString) throws RoomStringParseException {
    if (userRepository.findByUsername(username).isPresent()) {
      log.info("{} exists", username);
      return null;
    }
    Room room = SplitRoomStringUtil.split(roomString);

    Optional<Room> roomOptional = roomRepository.findByBuildAndUnitAndRoom(room.getBuild(), room.getUnit(), room.getRoom());
    if (roomOptional.isPresent()) {
      Optional<User> userOptional = userRepository.findByRoomId(roomOptional.get().getId());
      if (!userOptional.isPresent()) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
        user.setRoomId(roomOptional.get().getId());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userRepository.save(user);
        log.info("{}:{} register success!", roomString, username);
        return user;
      } else {
        log.info("{} was registered!", roomString);
        return null;
      }
    }
    log.info("{} not exists!", roomString);
    return null;
  }

  public boolean checkUsernameExists(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public boolean checkRoomExists(String roomString) {
    Room room = SplitRoomStringUtil.split(roomString);
    return roomRepository.findByBuildAndUnitAndRoom(room.getBuild(), room.getUnit(), room.getRoom()).isPresent();
  }

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private UserRepository userRepository;
}
