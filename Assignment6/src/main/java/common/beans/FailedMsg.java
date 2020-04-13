package common.beans;

import common.CommonConstants;
import common.utils.ConvertUtil;
import common.utils.ArrayUtil;
import java.util.Arrays;
import java.util.Objects;

/**
 * The type Failed msg.
 */
public class FailedMsg extends AbstractChatRoomProtocol {

  private String content;

  /**
   * Instantiates a new Failed msg.
   *
   * @param content the content
   */
  public FailedMsg(String content) {
    this.content = content;
  }

  /**
   * Gets content.
   *
   * @return the content
   */
  public String getContent() {
    return content;
  }

  /**
   * Serialize the object, the result could be transferred between server and client.
   *
   * @return the byte array
   */
  @Override
  public byte[] toByteArray() {
    byte[] contentBytes = content.getBytes();
    byte[] lengthBytes = ConvertUtil.intToByteArray(contentBytes.length);
    byte[] header = ConvertUtil.intToByteArray(CommonConstants.FAILED_MESSAGE);
    return ArrayUtil.concat(Arrays.asList(header, lengthBytes, contentBytes));
  }

  /**
   * Deserialize the byte array.
   *
   * @return the message
   */
  @Override
  public String getMessage() {
    return content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FailedMsg failedMsg = (FailedMsg) o;
    return content.equals(failedMsg.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content);
  }

  @Override
  public String toString() {
    return "FailedMsg{" +
        "content='" + content + '\'' +
        "} " + super.toString();
  }
}
