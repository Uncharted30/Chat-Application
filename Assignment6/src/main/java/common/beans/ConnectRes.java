package common.beans;

import common.CommonConstants;
import common.ConvertUtil;
import common.beans.interfaces.ChatRoomProtocol;
import common.utils.ArrayUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ConnectRes implements ChatRoomProtocol {

  private boolean status;
  private String content;

  public ConnectRes(boolean status, String content) {
    this.status = status;
    this.content = content;
  }

  public boolean getStatus() {
    return status;
  }

  public String getContent() {
    return content;
  }

  @Override
  public byte[] toByteArray() {
    byte[] contentBytes = this.content.getBytes();
    byte[] lengthBytes = ConvertUtil.intToByteArray(contentBytes.length);
    byte[] headerBytes = ConvertUtil.intToByteArray(CommonConstants.CONNECT_RESPONSE);
    byte[] statusBytes = new byte[]{ConvertUtil.booleanToByte(status)};
    return ArrayUtil.concat(
        new ArrayList<>(Arrays.asList(headerBytes, statusBytes, lengthBytes, contentBytes)));
  }

  @Override
  public String getMessage() {
    return this.content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectRes that = (ConnectRes) o;
    return status == that.status &&
        content.equals(that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, content);
  }

  @Override
  public String toString() {
    return "ConnectRes{" +
        "status=" + status +
        ", content='" + content + '\'' +
        '}';
  }
}
