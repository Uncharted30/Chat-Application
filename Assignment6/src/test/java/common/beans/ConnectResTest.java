package common.beans;

import static org.junit.Assert.*;

import common.CommonConstants;
import common.utils.ArrayUtil;
import common.utils.ConvertUtil;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ConnectResTest {
  ConnectRes connectRes;
  String content;
  boolean status;

  @Before
  public void setup() {
    content = "sdf";
    status = true;
    connectRes = new ConnectRes(status, content);
  }
  @Test
  public void getStatus() {
    assertEquals(status, connectRes.getStatus());
  }

  @Test
  public void getContent() {
    assertEquals(content, connectRes.getContent());
  }

  @Test
  public void toByteArray() {
    byte[] contentBytes = this.content.getBytes();
    byte[] lengthBytes = ConvertUtil.intToByteArray(contentBytes.length);
    byte[] headerBytes = ConvertUtil.intToByteArray(CommonConstants.CONNECT_RESPONSE);
    byte[] statusBytes = new byte[]{ConvertUtil.booleanToByte(status)};
    byte[] data =  ArrayUtil.concat(
        new ArrayList<>(Arrays.asList(headerBytes, statusBytes, lengthBytes, contentBytes)));
    assertArrayEquals(data, connectRes.toByteArray());
  }

  @Test
  public void getMessage() {
    assertEquals(content, connectRes.getMessage());
  }

  @Test
  public void testEquals() {
    ConnectRes connectRes2 = new ConnectRes(status, content);
    ConnectRes connectRes3 = new ConnectRes(false, content);
    assertFalse(connectRes2.equals(connectRes3));
    assertFalse(connectRes2.equals(null));
    assertFalse(connectRes2.equals("sdf"));
    assertTrue(connectRes2.equals(connectRes));
    assertTrue(connectRes2.equals(connectRes2));
  }

  @Test
  public void testHashCode() {
    ConnectRes connectRes2 = new ConnectRes(status, content);
    ConnectRes connectRes3 = new ConnectRes(false, content);
    assertEquals(connectRes2.hashCode(), connectRes.hashCode());
    assertNotEquals(connectRes2.hashCode(), connectRes3.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("ConnectRes{" +
        "status=" + status +
        ", content='" + content + '\'' +
        '}', connectRes.toString());
  }
}