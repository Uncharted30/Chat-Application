package common.beans;

import common.beans.interfaces.ChatRoomProtocol;

public class UserQuery implements ChatRoomProtocol {

  private String username;

  public UserQuery(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public byte[] toByteArray() {
    return new byte[0];
  }

  @Override
  public void print() {
    System.out.println("User " + this.username + "is requesting user list.");
  }
}
