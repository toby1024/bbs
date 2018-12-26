package info.asshead.bbs.web;

import info.asshead.bbs.annotation.Auth;
import info.asshead.bbs.common.CommonConstant;
import info.asshead.bbs.entity.Message;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/bbs")
@Slf4j
@Auth
public class MessageController {

  @GetMapping
  public String index(@RequestParam(defaultValue = "0") int page, Model model) {
    model.addAttribute("page", messageService.list(page));
    return "messages/index";
  }

  @GetMapping("publish")
  public String publish() {
    return "messages/create";
  }

  @PostMapping("create")
  public String create(@RequestParam String title, @RequestParam String info, HttpServletRequest request) {

    User user = (User) request.getSession().getAttribute(CommonConstant.SESSION_USER);

    Message message = messageService.create(title, info, user.getId());

    return "redirect:/bbs";
  }

  @GetMapping("my")
  public String my(@RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model) {
    User user = (User) request.getSession().getAttribute(CommonConstant.SESSION_USER);
    model.addAttribute("page", messageService.my(user.getId(), page));
    return "messages/my";
  }

  @GetMapping("{id}/delete")
  public String delete(@PathVariable int id, HttpServletRequest request){
   log.info("{} delete message {}", ((User)request.getSession().getAttribute(CommonConstant.SESSION_USER)).getUsername(), id);
    messageService.delete(id);
    return "redirect:/bbs/my";
  }
  @Autowired
  private MessageService messageService;
}
