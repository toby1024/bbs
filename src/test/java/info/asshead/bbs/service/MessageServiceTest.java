package info.asshead.bbs.service;

import info.asshead.bbs.entity.Message;
import info.asshead.bbs.repository.MessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MessageServiceTest {

  @Test
  public void create() {
    Message message = messageService.create("title","info", 1);
    assertTrue("should created", message.getTitle().equals("title"));
  }

  @Test
  public void my() {
    messageService.create("title","info", 1);
    messageService.create("title2","info2", 1);
    Page<Message> page = messageService.my(1, 0);

    assertTrue(page.getTotalElements()==2);
    List<Message> list = page.getContent();
    assertTrue(list.size() == 2);
    assertTrue(list.get(0).getUserId() == 1);
  }
  @Autowired
  private MessageService messageService;
}