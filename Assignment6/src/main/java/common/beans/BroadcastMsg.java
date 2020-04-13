package common.beans;

import common.CommonConstants;
import common.ConvertUtil;
import common.utils.ArrayUtil;
import java.util.Arrays;
import java.util.Objects;

public class BroadcastMsg extends Message {

  public BroadcastMsg(String sender, byte[] content) {
    super(sender, null, content);
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
