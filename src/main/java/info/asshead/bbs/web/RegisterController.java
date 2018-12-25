package info.asshead.bbs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jason
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

  public String register(){
    return "index";
  }
}
