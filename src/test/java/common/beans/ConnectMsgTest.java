package common.beans;

import static org.junit.Assert.*;

import common.CommonConstants;
import common.utils.ArrayUtil;
import common.utils.ConvertUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ConnectMsgTest {
  ConnectMsg connectMsg;
  String username;

  @Before
  public void setup() {
    username = "haolan";
    connectMsg = new ConnectMsg(username);
  }
  @Test
  public void getUsername() {
    assertEquals(username, connectMsg.getUsername());
  }

  @Test
  public void toByteArray() {
    List<byte[]> bytesList = new ArrayList<>();
    bytesList.add(ConvertUtil.intToByteArray(CommonConstants.CONNECT_MESSAGE));
    byte[] strBytes = username.getBytes();
    byte[] lengthBytes = ConvertUtil.intToByteArray(strBytes.length);
    bytesList.add(lengthBytes);
    bytesList.add(strBytes);
    assertArrayEquals(ArrayUtil.concat(bytesList), connectMsg.toByteArray());
  }

  @Test
  public void getMessage() {
    assertEquals("User " + username + " is trying to connect to the server.", connectMsg.getMessage());
  }

  @Test
  public void testEquals() {
    ConnectMsg connectMsg2 = new ConnectMsg("hah");
    ConnectMsg connectMsg3 = new ConnectMsg(username);
    assertFalse(connectMsg2.equals(connectMsg));
    assertFalse(connectMsg2.equals("sdfs"));
    assertFalse(connectMsg2.equals(null));
    assertTrue(connectMsg.equals(connectMsg));
  }

  @Test
  public void testHashCode() {
    ConnectMsg connectMsg2 = new ConnectMsg("hah");
    ConnectMsg connectMsg3 = new ConnectMsg(username);
    assertEquals(connectMsg.hashCode(), connectMsg3.hashCode());
    assertNotEquals(connectMsg.hashCode(), connectMsg2.hashCode());
  }
}