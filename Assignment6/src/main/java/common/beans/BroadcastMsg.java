package common.beans;

import common.CommonConstants;
import common.ConvertUtil;
import common.utils.ArrayUtil;
import java.util.Arrays;

public class BroadcastMsg extends Message {

  private String sender;
  private byte[] content;

  public BroadcastMsg(String sender, byte[] content) {
    this.sender = sender;
    this.content = content;
  }

  @Override
  public String getSender() {
    return sender;
  }

  @Override
  public String getRecipient() {
    return null;
  }

  public byte[] getContent() {
    return content;
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
    return new String(this.content);
  }


}
