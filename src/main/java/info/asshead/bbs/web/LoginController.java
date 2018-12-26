package info.asshead.bbs.web;

import info.asshead.bbs.common.CommonConstant;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

  @GetMapping
  public String index(){
    return "index";
  }

  @PostMapping
  public String login(@RequestParam String username,
                      @RequestParam String password, Model model, HttpServletRequest request){
    log.info("{} exec login", username);
    User user = loginService.login(username, password);
    if(user == null){
      model.addAttribute("message","登录失败，请确认用户名密码是否正确");
      return "index";
    }else {
      request.getSession().setAttribute(CommonConstant.SESSION_USER, user);
      return "redirect:/bbs";
    }
  }

  @Autowired
  private LoginService loginService;
}
