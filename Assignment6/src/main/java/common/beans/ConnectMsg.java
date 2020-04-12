package common.beans;

import common.beans.interfaces.ChatRoomProtocol;

public class ConnectMsg extends AbstractChatRoomProtocol {

  String username;

  public ConnectMsg(String username) {
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
  public String getMessage() {
    return "User " + this.username + " is trying to connect to the server.";
  }
}
