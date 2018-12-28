package info.asshead.bbs.service;

import info.asshead.bbs.entity.Message;
import info.asshead.bbs.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MessageServiceTest {

  User user = new User();

  @Before
  public void startUp() {
    user = registerService.register("testUser", "password", "1-1-101");
  }

  @Test
  public void create() {
    Message message = messageService.create("title", "info", user);
    assertTrue("should created", message.getTitle().equals("title"));
  }

  @Test
  public void my() {
    messageService.create("title", "info", user);
    messageService.create("title2", "info2", user);
    Page<Message> page = messageService.my(user.getId(), 0);

    assertTrue(page.getTotalElements() == 2);
    List<Message> list = page.getContent();
    assertTrue(list.size() == 2);
    assertTrue(list.get(0).getUser().getId() == user.getId());
  }

  @Autowired
  private MessageService messageService;

  @Autowired
  private RegisterService registerService;
}