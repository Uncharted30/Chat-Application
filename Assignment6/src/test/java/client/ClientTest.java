package client;

import common.CommonConstants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import org.junit.Before;
import server.ChatRoomServer;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class ClientTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  int port = 8008;
  String username = "haolan";


  private void setSystemIn(String input) {
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
  }

  @Test
  public void test() throws IOException {
    ChatRoomServer chatRoomServer = new ChatRoomServer(port);
    chatRoomServer.startServer();
    setSystemIn("who" + System.lineSeparator()
        + "@user" + System.lineSeparator() + username + System.lineSeparator() + "hello" + System.lineSeparator()
        + "@user" + System.lineSeparator() + "sfsf" + System.lineSeparator() + "hello" + System.lineSeparator()
        + "@all" + System.lineSeparator() + "hello world!" + System.lineSeparator()
        + "!user" + System.lineSeparator() + username + System.lineSeparator()
        + "logoff" + System.lineSeparator() + "sf" + System.lineSeparator());
    Client client = new Client(CommonConstants.DEFAULT_SERVER_IP, port, username);
    client.run();
  }
}
