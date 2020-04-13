package common.beans;

import common.CommonConstants;
import common.utils.ConvertUtil;
import common.utils.ArrayUtil;
import java.util.Arrays;

public class BroadcastMsg extends Message {

  public BroadcastMsg(String sender, byte[] content) {
    super(sender, "", content);
  }

  @Override
  public byte[] toByteArray() {
    byte[] headerBytes = ConvertUtil.intToByteArray(CommonConstants.BROADCAST_MESSAGE);
    byte[] lenBytes = ConvertUtil.intToByteArray(this.content.length);
    byte[] senderBytes = this.sender.getBytes();
    byte[] senderLenBytes = ConvertUtil.intToByteArray(senderBytes.length);
    return ArrayUtil
        .concat(Arrays.asList(headerBytes, senderLenBytes, senderBytes, lenBytes, this.content));
  }

  @Override
  public String getMessage() {
    return this.sender + " [Broadcast]: " + new String(this.content);
  }
}
