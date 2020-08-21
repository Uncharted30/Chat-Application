package common.beans;

import common.CommonConstants;
import java.util.Objects;

/**
 * The type Connect msg.
 */
public class ConnectMsg extends AbstractChatRoomProtocol {

  /**
   * The Username.
   */
  private String username;

  /**
   * Instantiates a new Connect msg.
   *
   * @param username the username
   */
  public ConnectMsg(String username) {
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
    return this.toByteArray(CommonConstants.CONNECT_MESSAGE, this.username);
  }

  /**
   * Deserialize the byte array.
   *
   * @return the message
   */
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
