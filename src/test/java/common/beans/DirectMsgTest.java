package common.beans;

import static org.junit.Assert.*;

import common.CommonConstants;
import common.utils.ArrayUtil;
import common.utils.ConvertUtil;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DirectMsgTest {
  DirectMsg directMsg;
  String sender = "haolan";
  String recipient = "pan";
  byte[] content = "hah".getBytes();
  @Before
  public void setup() {
    directMsg = new DirectMsg(sender, recipient, content);
  }

  @Test
  public void getMessage() {
    assertEquals( sender + " [Direct]: " + new String(content), directMsg.getMessage());
  }
}