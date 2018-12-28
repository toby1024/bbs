package info.asshead.bbs.repository;

import com.google.common.collect.Iterables;
import info.asshead.bbs.entity.User;
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
public class UserRepositoryTest {

  @Test
  public void shouldSaveUser(){
    User user = new User();
    user.setUsername("test");
    userRepository.save(user);

    Optional<User> u = userRepository.findById(user.getId());
    assertTrue("user is save", u.get().getUsername().equals("test"));

    Iterable<User> it = userRepository.findAll();
    assertTrue(Iterables.size(it) == 1);
  }

  @Autowired
  private UserRepository userRepository;
}