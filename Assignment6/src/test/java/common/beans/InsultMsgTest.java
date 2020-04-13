package common.beans;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InsultMsgTest {
  InsultMsg insultMsg;
  String sender = "haolan";
  String recipient = "pan";

  @Before
  public void setup() {
    insultMsg = new InsultMsg(sender, recipient);
  }

  @Test
  public void getSender() {
    assertEquals(sender, insultMsg.getSender());
  }

  @Test
  public void getRecipient() {
    assertEquals(recipient, insultMsg.getRecipient());
  }

  @Test
  public void getMessage() {
    assertNull(insultMsg.getMessage());
  }

  @Test
  public void testEquals() {
    InsultMsg insultMsg1 = new InsultMsg(sender, recipient);
    InsultMsg insultMsg2 = new InsultMsg("hsd", recipient);
    assertTrue(insultMsg1.equals(insultMsg));
    assertTrue(insultMsg1.equals(insultMsg1));
    assertFalse(insultMsg1.equals(insultMsg2));
    assertFalse(insultMsg1.equals(null));
    assertFalse(insultMsg1.equals("sf"));
  }

  @Test
  public void testHashCode() {
    InsultMsg insultMsg1 = new InsultMsg(sender, recipient);
    InsultMsg insultMsg2 = new InsultMsg("hsd", recipient);
    assertEquals(insultMsg1.hashCode(), insultMsg.hashCode());
    assertNotEquals(insultMsg2.hashCode(), insultMsg.hashCode());
  }

  @Test
  public void testToStirng() {
    assertEquals("InsultMsg{" +
        "sender='" + sender + '\'' +
        ", recipient='" + recipient + '\'' +
        "} " , insultMsg.toString());
  }
}