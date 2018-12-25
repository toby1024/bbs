package info.asshead.bbs.repository;

import info.asshead.bbs.entity.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoomRepositoryTest {

  @Before
  public void setUp() throws Exception {
    Room room = new Room();
    room.setBuild(1);
    room.setUnit(1);
    room.setRoom(101);
    roomRepository.save(room);

  }

  @Test
  public void findByBuildAndAndUnitAndRoom() {
    Optional<Room> room = roomRepository.findByBuildAndAndUnitAndRoom(1,1,101);

    assertTrue(room.get().getBuild() == 1);

    assertTrue(room.get().getRoomString().equals("1-1-101"));
  }
  @Autowired
  private RoomRepository roomRepository;
}