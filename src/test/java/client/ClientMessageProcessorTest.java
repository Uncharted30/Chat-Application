package client;

import static org.junit.Assert.*;

import common.beans.ConnectMsg;
import common.beans.ConnectRes;
import common.beans.FailedMsg;
import common.beans.QueryRes;
import common.beans.interfaces.ChatRoomProtocol;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ClientMessageProcessorTest {

  private DataInputStream getDataInputStream(byte[] data) {
    return new DataInputStream(new ByteArrayInputStream(data));
  }
  private byte[] getData(ChatRoomProtocol chatRoomProtocol) {
    byte[] msg = chatRoomProtocol.toByteArray();
    return Arrays.copyOfRange(msg, 5, msg.length);
  }
  @Test
  public void processDisconnectResMsg() throws IOException {
    boolean status = true;
    String msg = "hahaha";
    ConnectRes connectMsg = new ConnectRes(status, msg);
    DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(getData(connectMsg)));
    ClientMessageProcessor clientMessageProcessor = new ClientMessageProcessor(dataInputStream);
    assertEquals(connectMsg, clientMessageProcessor.processDisconnectResMsg());
  }

  @Test
  public void processFailedMsg() throws IOException {
    String msg = "hahaha";
    FailedMsg failedMsg = new FailedMsg(msg);
    DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(getData(failedMsg)));
    ClientMessageProcessor clientMessageProcessor = new ClientMessageProcessor(dataInputStream);
    assertEquals(failedMsg, clientMessageProcessor.processFailedMsg());
  }

  @Test
  public void processConnectResMsg() throws IOException {
    boolean status = true;
    String msg = "hahaha";
    ConnectRes connectMsg = new ConnectRes(status, msg);
    DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(getData(connectMsg)));
    ClientMessageProcessor clientMessageProcessor = new ClientMessageProcessor(dataInputStream);
    assertEquals(connectMsg, clientMessageProcessor.processConnectResMsg());
  }

  @Test
  public void processQueryMsg() throws IOException {
    List<String> users = new ArrayList<>(Arrays.asList("haolan", "pan"));
    QueryRes queryRes = new QueryRes(users);
    DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(getData(queryRes)));
    ClientMessageProcessor clientMessageProcessor = new ClientMessageProcessor(dataInputStream);
    assertEquals(queryRes, clientMessageProcessor.processQueryMsg());

  }
}