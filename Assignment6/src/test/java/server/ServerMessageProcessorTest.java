package server;

import static org.junit.Assert.*;

import common.CommonConstants;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import common.beans.interfaces.ChatRoomProtocol;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class ServerMessageProcessorTest {

  private byte[] getData(ChatRoomProtocol chatRoomProtocol) {
    byte[] msg = chatRoomProtocol.toByteArray();
    return Arrays.copyOfRange(msg, 5, msg.length);
  }

  @Test
  public void processDisconnectMsg() throws IOException {
    DisconnectMsg disconnectMsg = new DisconnectMsg("Tony Stark");
    DataInputStream dataInputStream = new DataInputStream(
        new ByteArrayInputStream(getData(disconnectMsg)));
    ServerMessageProcessor processor = new ServerMessageProcessor(dataInputStream);
    DisconnectMsg disconnectMsg1 = processor.processDisconnectMsg();
    assertEquals(disconnectMsg, disconnectMsg1);
  }

  @Test
  public void processUserQuery() throws IOException {
    UserQuery userQuery = new UserQuery("Tony Stark");
    DataInputStream dataInputStream = new DataInputStream(
        new ByteArrayInputStream(getData(userQuery)));
    ServerMessageProcessor processor = new ServerMessageProcessor(dataInputStream);
    UserQuery userQuery1 = processor.processUserQuery();
    assertEquals(userQuery, userQuery1);
  }

  @Test
  public void processInsultMsg() throws IOException {
    InsultMsg insultMsg = new InsultMsg("Tony Stark", "Steve Rogers");
    DataInputStream dataInputStream = new DataInputStream(
        new ByteArrayInputStream(getData(insultMsg)));
    ServerMessageProcessor serverMessageProcessor = new ServerMessageProcessor(dataInputStream);
    InsultMsg insultMsg1 = serverMessageProcessor.processInsultMsg();
    assertEquals(insultMsg, insultMsg1);
  }

  @Test
  public void testEquals() {
    InsultMsg insultMsg = new InsultMsg("Tony Stark", "Steve Rogers");
    DataInputStream dataInputStream1 = new DataInputStream(
        new ByteArrayInputStream(insultMsg.toByteArray()));
    UserQuery userQuery = new UserQuery("Tony Stark");
    DataInputStream dataInputStream2 = new DataInputStream(
        new ByteArrayInputStream(userQuery.toByteArray()));
    ServerMessageProcessor processor1 = new ServerMessageProcessor(dataInputStream1);
    ServerMessageProcessor processor2 = new ServerMessageProcessor(dataInputStream2);
    assertNotEquals(processor1, processor2);
    assertNotEquals(null, processor1);
    assertNotEquals(new ArrayList<>(), processor1);
    processor2 = new ServerMessageProcessor(
        new DataInputStream(new ByteArrayInputStream(new InsultMsg("a", "b").toByteArray())));
    assertNotEquals(processor1, processor2);
    processor2 = new ServerMessageProcessor(
        new DataInputStream(new ByteArrayInputStream(new InsultMsg("Tony Stark", "b").toByteArray())));
    assertNotEquals(processor1, processor2);
    assertEquals(processor1, processor1);
  }

  @Test
  public void testHashCode() {
    InsultMsg insultMsg = new InsultMsg("Tony Stark", "Steve Rogers");
    DataInputStream dataInputStream1 = new DataInputStream(
        new ByteArrayInputStream(insultMsg.toByteArray()));
    UserQuery userQuery = new UserQuery("Tony Stark");
    DataInputStream dataInputStream2 = new DataInputStream(
        new ByteArrayInputStream(userQuery.toByteArray()));
    ServerMessageProcessor processor1 = new ServerMessageProcessor(dataInputStream1);
    ServerMessageProcessor processor2 = new ServerMessageProcessor(dataInputStream2);
    assertNotEquals(processor1.hashCode(), processor2.hashCode());
    assertEquals(processor1.hashCode(), processor1.hashCode());
  }
}