package info.asshead.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Room {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;
  private int build;
  private int unit;
  private int room;

  public String getRoomString(){
    return build + "-" + unit + "-" + room;
  }
}
