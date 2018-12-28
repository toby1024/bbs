package info.asshead.bbs.service;

import info.asshead.bbs.entity.Room;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.repository.RoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RegisterServiceTest {

  @Test
  public void registerSuccess() throws Exception {
    Room room = roomRepository.findByBuildAndUnitAndRoom(1,1,101).get();
    User user = registerService.register("test", "test", "1-1-101");

    assertTrue(user.getRoomId() == room.getId());
    assertTrue(user.getUsername().equals("test"));
  }

  @Test
  public void registerRepeat() throws Exception {
    registerService.register("test", "test", "1-1-101");

    User user = registerService.register("testA", "testA", "1-1-101");
    assertNull(user);
  }

  @Test
  public void registerRoomNotExists() throws Exception {

    User user = registerService.register("testA", "testA", "30-1-101");
    assertNull(user);
  }

  @Autowired
  private RoomRepository roomRepository;
  @Autowired
  private RegisterService registerService;
}