package info.asshead.bbs.service;

import com.google.common.hash.Hashing;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class LoginServiceTest {

  @Test
  public void loginShouldReturnNull() {
    User user = loginService.login("test","test");
    assertTrue(user == null);
  }

  @Test
  public void loginShouldReturnUser(){
    User user = new User();
    user.setUsername("test");
    user.setPassword(Hashing.sha256().hashString("test", StandardCharsets.UTF_8).toString());
    userRepository.save(user);

    User LoginUser = loginService.login("test","test");

    assertTrue(LoginUser.getUsername().equals(user.getUsername()));
  }


  @Test
  public void loginShouldfailed(){
    User user = new User();
    user.setUsername("test");
    user.setPassword(Hashing.sha256().hashString("test", StandardCharsets.UTF_8).toString());
    userRepository.save(user);

    User LoginUser = loginService.login("test","ddd");

    assertTrue(LoginUser == null);
  }

  @Autowired
  private LoginService loginService;

  @Autowired
  private UserRepository userRepository;
}