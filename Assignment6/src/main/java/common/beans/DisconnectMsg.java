package common.beans;

import common.CommonConstants;
import java.util.Objects;

/**
 * The type Disconnect msg.
 */
public class DisconnectMsg extends AbstractChatRoomProtocol {

  private String username;

  /**
   * Instantiates a new Disconnect msg.
   *
   * @param username the username
   */
  public DisconnectMsg(String username) {
    this.username = username;
  }

  /**
   * Gets username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Serialize the object, the result could be transferred between server and client.
   *
   * @return the byte array
   */
  @Override
  public byte[] toByteArray() {
    return this.toByteArray(CommonConstants.DISCONNECT_MESSAGE, this.username);
  }
  /**
   * Deserialize the byte array.
   *
   * @return the message
   */
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
