package common.beans;

import common.CommonConstants;
import java.util.Objects;

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
    return this.toByteArray(CommonConstants.CONNECT_MESSAGE, this.username);
  }

  @Override
  public String getMessage() {
    return "User " + this.username + " is trying to connect to the server.";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectMsg that = (ConnectMsg) o;
    return username.equals(that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }


}
