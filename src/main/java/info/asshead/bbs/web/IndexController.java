package info.asshead.bbs.web;

import info.asshead.bbs.common.CommonConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping
public class IndexController {

  @GetMapping("/")
  public String index(HttpServletRequest request){
    if(request.getSession().getAttribute(CommonConstant.SESSION_USER) != null){
      return "redirect:/bbs";
    }
    return "index";
  }
}
