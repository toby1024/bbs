package info.asshead.bbs.job;

import info.asshead.bbs.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author jason
 */
@Component
@Slf4j
public class CleanMessageJob {

  @Value("expire_day")
  private String expireDay;

  @Scheduled(cron = "0 0 0 1 * ?")
  public void cleanMessage() {
    int day = 7;
    if (!StringUtils.isEmpty(expireDay)) {
      day = Integer.parseInt(expireDay);
    }
    log.info("开始清理{}天前的消息", day);
    messageService.cleanMessage(day);
    log.info("清理{}天前的消息完成", day);
  }

  @Autowired
  private MessageService messageService;
}
