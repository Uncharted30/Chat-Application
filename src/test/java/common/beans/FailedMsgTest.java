package common.beans;

import static org.junit.Assert.*;

import common.CommonConstants;
import common.utils.ArrayUtil;
import common.utils.ConvertUtil;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class FailedMsgTest {
  FailedMsg failedMsg;
  String content;
  @Before
  public void setup() {
    content = "jaja";
    failedMsg = new FailedMsg(content);
  }
  @Test
  public void getContent() {
    assertEquals(content, failedMsg.getContent());
  }

  @Test
  public void toByteArray() {
    byte[] contentBytes = content.getBytes();
    byte[] lengthBytes = ConvertUtil.intToByteArray(contentBytes.length);
    byte[] header = ConvertUtil.intToByteArray(CommonConstants.FAILED_MESSAGE);
    assertArrayEquals(ArrayUtil.concat(Arrays.asList(header, lengthBytes, contentBytes)), failedMsg.toByteArray());
  }

  @Test
  public void getMessage() {
    assertEquals(content, failedMsg.getMessage());
  }

  @Test
  public void testEquals() {
    FailedMsg failedMsg1 = new FailedMsg(content);
    FailedMsg failedMsg2 = new FailedMsg("hah");
    assertTrue(failedMsg1.equals(failedMsg));
    assertTrue(failedMsg1.equals(failedMsg1));
    assertFalse(failedMsg1.equals(failedMsg2));
    assertFalse(failedMsg1.equals(null));
    assertFalse(failedMsg1.equals("sf"));
  }

  @Test
  public void testHashCode() {
    FailedMsg failedMsg1 = new FailedMsg(content);
    FailedMsg failedMsg2 = new FailedMsg("hah");
    assertEquals(failedMsg1.hashCode(), failedMsg.hashCode());
    assertNotEquals(failedMsg1.hashCode(), failedMsg2.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("FailedMsg{" +
        "content='" + content + '\'' +
        "} ", failedMsg.toString());
  }
}