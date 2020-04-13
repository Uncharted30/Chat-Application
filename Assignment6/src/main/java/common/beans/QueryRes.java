package common.beans;

import common.CommonConstants;
import java.util.List;
import java.util.Objects;

/**
 * The type Query res.
 */
public class QueryRes extends AbstractChatRoomProtocol {

  private List<String> userList;

  /**
   * Instantiates a new Query res.
   *
   * @param userList the user list
   */
  public QueryRes(List<String> userList) {
    this.userList = userList;
  }

  /**
   * Gets user list.
   *
   * @return the user list
   */
  public List<String> getUserList() {
    return userList;
  }

  /**
   * Serialize the object, the result could be transferred between server and client.
   *
   * @return the byte array
   */
  @Override
  public byte[] toByteArray() {
    return this.toByteArray(CommonConstants.QUERY_USER_RESPONSE, userList.size(), userList);
  }

  /**
   * Deserialize the byte array.
   *
   * @return the message
   */
  @Override
  public String getMessage() {
    StringBuilder message = new StringBuilder(
        "There are " + (userList.size() + 1) + " in the chat room:");
    for (int i = 0; i < this.userList.size(); i++) {
      message.append("\n").append(i + 1).append(". ").append(userList.get(i));
    }
    return message.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryRes queryRes = (QueryRes) o;
    return userList.equals(queryRes.userList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userList);
  }

  @Override
  public String toString() {
    return "QueryRes{" +
        "userList=" + userList +
        "} " + super.toString();
  }
}
