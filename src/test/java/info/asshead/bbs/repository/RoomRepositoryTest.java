package info.asshead.bbs.repository;

import info.asshead.bbs.entity.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class RoomRepositoryTest {

  @Test
  public void findByBuildAndAndUnitAndRoom() {
    Optional<Room> room = roomRepository.findByBuildAndUnitAndRoom(1,1,101);

    assertTrue(room.get().getBuild() == 1);

    assertTrue(room.get().getRoomString().equals("1-1-101"));
  }

  @Autowired
  private RoomRepository roomRepository;
}