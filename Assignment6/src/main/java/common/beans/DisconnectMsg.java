package common.beans;

import common.CommonConstants;
import common.beans.interfaces.ChatRoomProtocol;
import java.util.Objects;

public class DisconnectMsg extends AbstractChatRoomProtocol {

  private String username;

  public DisconnectMsg(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public byte[] toByteArray() {
    return this.toByteArray(CommonConstants.DISCONNECT_MESSAGE, this.username);
  }

  @Override
  public String getMessage() {
    return "User " + this.username + "is trying to disconnect.";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DisconnectMsg that = (DisconnectMsg) o;
    return username.equals(that.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }

  @Override
  public String toString() {
    return "DisconnectMsg{" +
        "username='" + username + '\'' +
        "} " + super.toString();
  }
}
