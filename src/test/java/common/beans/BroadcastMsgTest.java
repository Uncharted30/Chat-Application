package common.beans;

import static org.junit.Assert.*;

import common.CommonConstants;
import common.utils.ArrayUtil;
import common.utils.ConvertUtil;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class BroadcastMsgTest {
  BroadcastMsg broadcastMsg;
  byte[] content;
  String sender;
  @Before
  public void setup() {
    content = new byte[10];
    sender = "haolan";
    broadcastMsg = new BroadcastMsg(sender, content);
  }
  @Test
  public void toByteArray() {
    byte[] headerBytes = ConvertUtil.intToByteArray(CommonConstants.BROADCAST_MESSAGE);
    byte[] lenBytes = ConvertUtil.intToByteArray(this.content.length);
    byte[] senderBytes = this.sender.getBytes();
    byte[] senderLenBytes = ConvertUtil.intToByteArray(senderBytes.length);
    assertArrayEquals( ArrayUtil
        .concat(Arrays.asList(headerBytes, senderLenBytes, senderBytes, lenBytes, content)), broadcastMsg.toByteArray());
  }

  @Test
  public void getMessage() {
    assertEquals("haolan" + " [Broadcast]: " + new String(content), broadcastMsg.getMessage());
  }

  @Test
  public void testEquals() {
    BroadcastMsg broadcastMsg1 = new BroadcastMsg(sender, content);
    BroadcastMsg broadcastMsg2 = new BroadcastMsg("sdf", content);
    assertTrue(broadcastMsg1.equals(broadcastMsg));
    assertTrue(broadcastMsg1.equals(broadcastMsg1));
    assertFalse(broadcastMsg1.equals(broadcastMsg2));
    assertFalse(broadcastMsg1.equals(null));
    assertFalse(broadcastMsg1.equals("sf"));
  }

  @Test
  public void testHashCode() {
    BroadcastMsg broadcastMsg1 = new BroadcastMsg(sender, content);
    BroadcastMsg broadcastMsg2 = new BroadcastMsg("sdf", content);
    assertEquals(broadcastMsg1.hashCode(), broadcastMsg.hashCode());
    assertNotEquals(broadcastMsg1.hashCode(), broadcastMsg2.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("Message{" +
        "sender='" + sender + '\'' +
        ", recipient='" + "" + '\'' +
        ", content=" + Arrays.toString(content) +
        "} ", broadcastMsg.toString());
  }
}