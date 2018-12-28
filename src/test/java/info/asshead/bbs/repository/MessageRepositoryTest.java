package info.asshead.bbs.repository;

import com.google.common.collect.Iterables;
import info.asshead.bbs.entity.Message;
import info.asshead.bbs.entity.User;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MessageRepositoryTest {

  @Test
  public void updateBySQLShouldUpdate1() {
    Message message = new Message();
    message.setTitle("title");
    message.setInfo("info");
    message.setStatus(1);
    message.setCreatedAt(DateUtil.tomorrow());
    messageRepository.save(message);

    message = new Message();
    message.setTitle("title1");
    message.setInfo("info1");
    message.setStatus(1);
    message.setCreatedAt(DateUtil.yesterday());
    messageRepository.save(message);

    messageRepository.findAll().forEach(m -> System.out.println(m.getTitle()));

    assertTrue(Iterables.size(messageRepository.findAll()) == 2);

    Date today = DateUtil.now();
    messageRepository.cleanByDate(today);
    Pageable pageable = PageRequest.of(0, 10);
    Page<Message> page = messageRepository.findByStatus(1, pageable);
    assertTrue(page.getTotalElements() == 1);

    Message message1 = page.getContent().get(0);
    assertTrue(message1.getTitle().equals("title"));
  }

  @Test
  public void testGetUsername(){
    User user = new User();
    user.setUsername("test");
    userRepository.save(user);

    Message message = new Message();
    message.setTitle("title");
    message.setInfo("info");
    message.setStatus(1);
    message.setCreatedAt(DateUtil.tomorrow());
    message.setUser(user);
    messageRepository.save(message);

    System.out.println(user.getId());
    Pageable pageable = PageRequest.of(0, 10);
    Page<Message> page = messageRepository.findByUserIdAndStatus(user.getId(),1, pageable);
    page.getContent().forEach(m ->{
      System.out.println(m.getUser().getUsername());
    });
  }

  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private UserRepository userRepository;
}