package common.beans;

import common.CommonConstants;
import common.ConvertUtil;
import common.beans.interfaces.ChatRoomProtocol;
import common.utils.ArrayUtil;
import java.util.Arrays;

public class FailedMsg implements ChatRoomProtocol {

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

}
