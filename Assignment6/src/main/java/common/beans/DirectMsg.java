package common.beans;

import common.CommonConstants;
import common.ConvertUtil;
import common.utils.ArrayUtil;
import java.util.Arrays;

public class DirectMsg extends Message {

  public DirectMsg(String sender, String recipient, byte[] content) {
    super(sender, recipient, content);
  }

  @Override
  public byte[] toByteArray() {
    byte[] bytes = this
        .toByteArray(CommonConstants.DIRECT_MESSAGE, Arrays.asList(this.sender, this.recipient));
    byte[] lengthBytes = ConvertUtil.intToByteArray(this.content.length);
    return ArrayUtil.concat(Arrays.asList(bytes, lengthBytes, this.content));
  }

  @Override
  public String getMessage() {
    return this.sender + " [Direct]: " + new String(content);
  }
}
