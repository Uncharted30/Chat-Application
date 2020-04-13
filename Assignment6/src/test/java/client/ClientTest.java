package client;

import common.CommonConstants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import server.ChatRoomServer;
import org.junit.Test;

public class ClientTest {
  int port = 8008;
  String username = "haolan";
  ChatRoomServer chatRoomServer = null;
  Client client = null;
  @Before
  public void setup() throws IOException {
    ChatRoomServer chatRoomServer = new ChatRoomServer(port);
    chatRoomServer.startServer();
  }

  private void setSystemIn(String input) {
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
  }

  @Test
  public void connect() throws IOException {
    setSystemIn("logoff" + System.lineSeparator() + System.lineSeparator());
    client = new Client(CommonConstants.DEFAULT_SERVER_IP, port, username);
    client.run();
  }
}
