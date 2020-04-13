package common.beans;

import common.CommonConstants;
import common.beans.interfaces.ChatRoomProtocol;
import java.util.Objects;

public class UserQuery extends AbstractChatRoomProtocol {

  private String username;

  public UserQuery(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public byte[] toByteArray() {
    return this.toByteArray(CommonConstants.QUERY_CONNECTED_USERS, this.username);
  }

  @Override
  public String getMessage() {
    return "User " + this.username + "is requesting user list.";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserQuery userQuery = (UserQuery) o;
    return username.equals(userQuery.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }

  @Override
  public String toString() {
    return "UserQuery{" +
        "username='" + username + '\'' +
        "} " + super.toString();
  }
}
