package info.asshead.bbs.web;

import info.asshead.bbs.entity.Room;
import info.asshead.bbs.entity.User;
import info.asshead.bbs.exception.RoomStringParseException;
import info.asshead.bbs.repository.RoomRepository;
import info.asshead.bbs.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jason
 */
@Controller
@RequestMapping("/register")
@Slf4j
public class RegisterController {

  @GetMapping
  public String register() {
    return "register/form";
  }

  @PostMapping
  public String register(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam int build,
                         @RequestParam int unit,
                         @RequestParam int room, Model model) {
    try {
      String roomString = build + "-" + unit + "-" + room;
      if (registerService.checkUsernameExists(username)) {
        model.addAttribute("message", "用户名已经存在");
        return "register/form";
      }
      if (!registerService.checkRoomExists(roomString)) {
        model.addAttribute("message", "房号不存在，本BBS只对杭宸业主开放");
        return "register/form";
      }
      User user = registerService.register(username, password, roomString);
      if (user != null) {
        return "redirect:/login";
      } else {
        model.addAttribute("message", "注册失败，房号被占用");
      }

    } catch (RoomStringParseException e) {
      log.error("room string parse error", e);
    }
    return "register/form";
  }

//  @GetMapping("init")
//  public String init(Model model) {
//    ArrayList singleList = new ArrayList();
//    singleList.add(1);
//    singleList.add(3);
//    singleList.add(5);
//    singleList.add(6);
//    singleList.add(10);
//    singleList.add(11);
//
//    ArrayList h = new ArrayList(3);
//    h.add(7);
//    h.add(8);
//
//    List<Room> list = new ArrayList<>(800);
//
//    for (int i = 1; i <= 11; i++) {
//      for (int j = 1; j <= 2; j++) {
//        for (int k = 1; k < 19; k++) {
//          for (int l = 1; l < 5; l++) {
//            if (singleList.contains(i) && j == 2) {
//              continue;
//            }
//            if (h.contains(i) && k > 11) {
//              continue;
//            }
//            if (i == 6 && k > 13) {
//              continue;
//            }
//            Room room = new Room();
//            room.setBuild(i);
//            room.setUnit(j);
//            room.setRoom(Integer.parseInt(k + "0" + l));
//            list.add(room);
//          }
//        }
//      }
//    }
//    list.forEach(r -> roomRepository.save(r));
//    model.addAttribute("message", "success");
//    return "register/form";
//  }

  @Autowired
  private RegisterService registerService;

  @Autowired
  private RoomRepository roomRepository;
}
