package info.asshead.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class User {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private int id;
  private String username;
  private String password;
  private String salt;

  private int roomId;

  private Date createdAt;
  private Date updatedAt;

  public User(){}
  public User(int userId) {
    this.id = userId;
  }
}
