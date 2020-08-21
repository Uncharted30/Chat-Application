package client;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import server.ChatRoomServer;

public class ClientRunnerTest {
  int port = 8008;
  String username = "haolan";

  @Test
  public void runWithWorngOption() {
    ClientRunner.main(new String[]{"-p", "8080"});
    ClientRunner.main(new String[]{"-p", "sdf0", "-u", "sdf"});
  }

  private void setSystemIn(String input) {
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
  }
  @Test
  public void run() throws IOException {
    ChatRoomServer chatRoomServer = new ChatRoomServer(port);
    chatRoomServer.startServer();
    setSystemIn("who" + System.lineSeparator()
        + "@user" + System.lineSeparator() + username + System.lineSeparator() + "hello" + System.lineSeparator()
        + "@user" + System.lineSeparator() + "sfsf" + System.lineSeparator() + "hello" + System.lineSeparator()
        + "@all" + System.lineSeparator() + "hello world!" + System.lineSeparator()
        + "!user" + System.lineSeparator() + username + System.lineSeparator()
        + "logoff" + System.lineSeparator() + "sf" + System.lineSeparator());
    ClientRunner.main(new String[]{"-p", "8008", "-u", "haolan"});
  }

}