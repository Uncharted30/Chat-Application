package common.beans;

import common.CommonConstants;
import common.utils.ConvertUtil;
import common.utils.ArrayUtil;
import java.util.Arrays;

/**
 * The type Broadcast msg.
 */
public class BroadcastMsg extends Message {

  /**
   * Instantiates a new Broadcast msg.
   *
   * @param sender  the sender
   * @param content the content
   */
  public BroadcastMsg(String sender, byte[] content) {
    super(sender, "", content);
  }

  /**
   * Serialize the object, the result could be transferred between server and client.
   *
   * @return the byte array
   */
  @Override
  public byte[] toByteArray() {
    byte[] headerBytes = ConvertUtil.intToByteArray(CommonConstants.BROADCAST_MESSAGE);
    byte[] lenBytes = ConvertUtil.intToByteArray(this.content.length);
    byte[] senderBytes = this.sender.getBytes();
    byte[] senderLenBytes = ConvertUtil.intToByteArray(senderBytes.length);
    return ArrayUtil
        .concat(Arrays.asList(headerBytes, senderLenBytes, senderBytes, lenBytes, this.content));
  }

  /**
   * Deserialize the byte array.
   *
   * @return the message
   */
  @Override
  public String getMessage() {
    return this.sender + " [Broadcast]: " + new String(this.content);
  }
}
