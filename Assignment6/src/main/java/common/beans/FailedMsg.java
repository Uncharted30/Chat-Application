package common.beans;

import common.CommonConstants;
import common.ConvertUtil;
import common.beans.interfaces.ChatRoomProtocol;
import common.utils.ArrayUtil;
import java.util.Arrays;
import java.util.Objects;

public class FailedMsg extends AbstractChatRoomProtocol {

  String content;

  public FailedMsg(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  @Override
  public byte[] toByteArray() {
    byte[] contentBytes = content.getBytes();
    byte[] lengthBytes = ConvertUtil.intToByteArray(contentBytes.length);
    byte[] header = ConvertUtil.intToByteArray(CommonConstants.FAILED_MESSAGE);
    return ArrayUtil.concat(Arrays.asList(header, lengthBytes, contentBytes));
  }

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
