package server;

import static org.junit.Assert.*;

import client.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientHandlerTest {

  private ChatRoomServer server;

  @Before
  public void setUp() throws Exception {
    this.server = new ChatRoomServer();
    this.server.startServer();
  }

  @After
  public void tearDown() throws Exception {
    this.server.stopServer();
  }

  @Test
  public void disconnect() {
  }

  @Test
  public void sendMessage() {
  }

  @Test
  public void run() {
  }

  @Test
  public void testEquals() {
  }

  @Test
  public void testHashCode() {
  }
}