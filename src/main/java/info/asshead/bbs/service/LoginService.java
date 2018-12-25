package info.asshead.bbs.service;

import com.google.common.hash.Hashing;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.repository.UserRepository;
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
public class LoginService {
  @Autowired
  private UserRepository userRepository;

  public User login(String username, String password){
     Optional<User> optionalUser = userRepository.findByUsername(username);
     if(optionalUser.isPresent()){
       User user = optionalUser.get();

       String sha256hex = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
       if(sha256hex.equals(user.getPassword())){
         log.info("{} was login successed", username);
         return user;
       }
     }
    log.info("{} was login failed", username);
     return null;
  }
}
