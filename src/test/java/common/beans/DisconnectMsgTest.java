package common.beans;

import static org.junit.Assert.*;

import common.CommonConstants;
import common.utils.ArrayUtil;
import common.utils.ConvertUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DisconnectMsgTest {
  DisconnectMsg disconnectMsg;
  String username;

  @Before
  public void setup() {
    username = "haolan";
    disconnectMsg = new DisconnectMsg(username);
  }
  @Test
  public void getUsername() {
    assertEquals(username, disconnectMsg.getUsername());
  }

  @Test
  public void toByteArray() {
    List<byte[]> bytesList = new ArrayList<>();
    bytesList.add(ConvertUtil.intToByteArray(CommonConstants.DISCONNECT_MESSAGE));
    byte[] strBytes = username.getBytes();
    byte[] lengthBytes = ConvertUtil.intToByteArray(strBytes.length);
    bytesList.add(lengthBytes);
    bytesList.add(strBytes);
    assertArrayEquals(ArrayUtil.concat(bytesList), disconnectMsg.toByteArray());
  }

  @Test
  public void getMessage() {
    assertEquals("User " + username + " is trying to disconnect.", disconnectMsg.getMessage());
  }

  @Test
  public void testEquals() {
    DisconnectMsg disconnectMsg1 = new DisconnectMsg("hah");
    DisconnectMsg disconnectMsg2 = new DisconnectMsg(username);
    assertTrue(disconnectMsg2.equals(disconnectMsg));
    assertFalse(disconnectMsg2.equals("sdfs"));
    assertFalse(disconnectMsg2.equals(null));
    assertTrue(disconnectMsg2.equals(disconnectMsg2));
  }

  @Test
  public void testHashCode() {
    DisconnectMsg disconnectMsg1 = new DisconnectMsg("hah");
    DisconnectMsg disconnectMsg2 = new DisconnectMsg(username);
    assertEquals(disconnectMsg2.hashCode(), disconnectMsg.hashCode());
    assertNotEquals(disconnectMsg2.hashCode(), disconnectMsg1.hashCode());
  }

  @Test
  public void TestToString() {
    assertEquals( "DisconnectMsg{" +
        "username='" + username + '\'' +
        "} ", disconnectMsg.toString());
  }
}