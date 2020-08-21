package common.beans;

import common.CommonConstants;
import common.utils.ConvertUtil;
import common.utils.ArrayUtil;
import java.util.Arrays;

/**
 * The type Direct msg.
 */
public class DirectMsg extends Message {

  /**
   * Instantiates a new Direct msg.
   *
   * @param sender    the sender
   * @param recipient the recipient
   * @param content   the content
   */
  public DirectMsg(String sender, String recipient, byte[] content) {
    super(sender, recipient, content);
  }

  /**
   * Serialize the object, the result could be transferred between server and client.
   *
   * @return the byte array
   */
  @Override
  public byte[] toByteArray() {
    byte[] bytes = this
        .toByteArray(CommonConstants.DIRECT_MESSAGE, Arrays.asList(this.sender, this.recipient));
    byte[] lengthBytes = ConvertUtil.intToByteArray(this.content.length);
    return ArrayUtil.concat(Arrays.asList(bytes, lengthBytes, this.content));
  }

  /**
   * Deserialize the byte array.
   *
   * @return the message
   */
  @Override
  public String getMessage() {
    return this.sender + " [Direct]: " + new String(content);
  }
}
