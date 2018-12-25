package info.asshead.bbs.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jason
 */
@RequestMapping("/index")
public class IndexControler {

  @GetMapping
  public String index(){
    return "index";
  }
}
