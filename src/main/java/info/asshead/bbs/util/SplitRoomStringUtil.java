package info.asshead.bbs.util;

import info.asshead.bbs.entity.Room;

/**
 * @author jason
 */
public class SplitRoomStringUtil {

  public static Room split(String roomString) throws Exception{
    String[] array = roomString.split("-");
    Room room = new Room();
    room.setBuild(Integer.parseInt(array[0]));
    room.setUnit(Integer.parseInt(array[1]));
    room.setRoom(Integer.parseInt(array[2]));
    return room;
  }
}
