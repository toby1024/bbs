package info.asshead.bbs.service;

import info.asshead.bbs.entity.Message;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


/**
 * @author jason
 */
@Service
@Slf4j
public class MessageService {
  private static int pageSize = 15;

  public Message create(String title, String info, User user) {
    Message message = new Message();
    message.setUser(user);
    message.setTitle(title);
    message.setInfo(info);
    message.setStatus(1);
    message.setCreatedAt(new Date());
    message.setUpdatedAt(new Date());

    messageRepository.save(message);
    return message;
  }

  public Page<Message> my(int userId, int page) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    return messageRepository.findByUserIdAndStatus(userId, 1, pageable);
  }

  public Page<Message> list(int page) {
    Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    return messageRepository.findByStatus(1, pageable);
  }

  public void delete(int id) {
    Optional<Message> optional = messageRepository.findById(id);
    if (optional.isPresent()) {
      Message message = optional.get();
      message.setStatus(0);
      messageRepository.save(message);
    }
  }

  public void cleanMessage(int overDay) {
    Calendar now = Calendar.getInstance();
    now.add(Calendar.DATE, -overDay);
    Date date = now.getTime();
    messageRepository.cleanByDate(date);
  }

  @Autowired
  private MessageRepository messageRepository;
}
