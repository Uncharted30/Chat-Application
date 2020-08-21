package server;

import static org.junit.Assert.*;

import client.Client;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChatRoomServerTest {

  private static final int port = 56350;
  private ChatRoomServer chatRoomServer;

  @Before
  public void setUp() throws Exception {
    this.chatRoomServer = new ChatRoomServer(port);
  }

  @After
  public void tearDown() throws Exception {
    this.chatRoomServer.stopServer();
  }

  @Test
  public void startServer() throws IOException, InterruptedException {
    this.chatRoomServer.startServer();
    assertTrue(chatRoomServer.isAlive());
    Client client = new Client("127.0.0.1", port, "Stark");
    client.run();
    this.chatRoomServer.stopServer();
    Thread.sleep(3000);
    assertFalse(chatRoomServer.isAlive());
  }

  @Test
  public void stopServer() throws InterruptedException {
    this.chatRoomServer.startServer();
    assertTrue(chatRoomServer.isAlive());
    this.chatRoomServer.stopServer();
    Thread.sleep(3000);
    assertFalse(chatRoomServer.isAlive());
  }

  @Test
  public void testEquals() throws IOException, InterruptedException {
    ChatRoomServer server2 = new ChatRoomServer();
    assertNotEquals(this.chatRoomServer, server2);
    assertNotEquals(server2, null);
    assertNotEquals(new ArrayList<>(), server2);
    assertEquals(server2, server2);
  }

  @Test
  public void testHashCode() throws IOException {
    ChatRoomServer server2 = new ChatRoomServer();
    assertEquals(server2.hashCode(), server2.hashCode());
    assertNotEquals(this.chatRoomServer.hashCode(), server2.hashCode());
  }

  @Test
  public void testTimeOutException() throws InterruptedException {
    this.chatRoomServer.startServer();
    Thread.sleep(5000);
  }
}