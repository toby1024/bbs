package info.asshead.bbs.service;

import info.asshead.bbs.entity.Message;
import info.asshead.bbs.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.file.OpenOption;
import java.util.Date;
import java.util.Optional;


/**
 * @author jason
 */
@Service
@Slf4j
public class MessageService {
  private static int pageSize = 15;

  public Message create(String title, String info, int userId) {
    Message message = new Message();
    message.setUserId(userId);
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

  public void delete(int id){
    Optional<Message> optional = messageRepository.findById(id);
    if(optional.isPresent()){
      Message message = optional.get();
      message.setStatus(0);
      messageRepository.save(message);
    }
  }

  @Autowired
  private MessageRepository messageRepository;
}
