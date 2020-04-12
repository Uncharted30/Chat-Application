package common.beans;

import common.CommonConstants;
import common.utils.ArrayUtil;
import java.util.Arrays;

public class DirectMsg extends Message {
  private String sender;
  private String recipient;
  private byte[] content;

  public DirectMsg(String sender, String recipient, byte[] content) {
    this.sender = sender;
    this.recipient = recipient;
    this.content = content;
  }

  @Override
  public String getSender() {
    return sender;
  }

  @Override
  public String getRecipient() {
    return recipient;
  }

  public byte[] getContent() {
    return content;
  }

  @Override
  public byte[] toByteArray() {
    byte[] bytes = this
        .toByteArray(CommonConstants.DIRECT_MESSAGE, Arrays.asList(this.sender, this.recipient));
    return ArrayUtil.concat(Arrays.asList(bytes, this.content));
  }

  @Override
  public String getMessage() {
    return new String(content);
  }
}
