package common.beans;

import common.CommonConstants;
import common.ConvertUtil;
import common.beans.interfaces.ChatRoomProtocol;
import common.utils.ArrayUtil;
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectRes implements ChatRoomProtocol {

  private Boolean status;
  private String content;

  public ConnectRes(Boolean status, String content) {
    this.status = status;
    this.content = content;
  }

  public ConnectRes(String content) {
    this.content = content;
  }

  public Boolean getStatus() {
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
    if (this.status != null) {
      byte[] statusBytes = new byte[]{ConvertUtil.booleanToByte(status)};
      return ArrayUtil.concat(
          new ArrayList<>(Arrays.asList(headerBytes, statusBytes, lengthBytes, contentBytes)));
    }
    return ArrayUtil.concat(
        new ArrayList<>(Arrays.asList(headerBytes, lengthBytes, contentBytes)));
  }

  @Override
  public void print() {

  }
}
