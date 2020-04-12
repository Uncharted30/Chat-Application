package common.beans.response;

import common.beans.requests.Request;

public class UserQuery extends Request {

  private String username;

  public UserQuery(String username) {
    this.username = username;
  }

  @Override
  public byte[] toByteArray() {
    return new byte[0];
  }

  @Override
  public void log() {
    System.out.println("User " + this.username + "is requesting user list.");
  }
}
