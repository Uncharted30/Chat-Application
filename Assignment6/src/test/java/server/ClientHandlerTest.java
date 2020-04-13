package server;

import static org.junit.Assert.*;
;
import common.CommonConstants;
import common.beans.BroadcastMsg;
import common.beans.ConnectMsg;
import common.beans.DirectMsg;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.interfaces.MessageAgent;

public class ClientHandlerTest {

  private static final int port = 56353;
  private static final String name = "Stark";

  private ChatRoomServer server;
  private Socket socket;
  private DataInputStream dataInputStream;
  private DataOutputStream dataOutputStream;

  @Before
  public void setUp() throws Exception {
    this.server = new ChatRoomServer(port);
    this.server.startServer();
    this.socket = new Socket("127.0.0.1", port);
    this.dataInputStream = new DataInputStream(socket.getInputStream());
    this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    this.dataOutputStream.write(new ConnectMsg(name).toByteArray());
    this.dataInputStream.readInt();
    this.dataInputStream.read();
    int len = this.dataInputStream.readInt();
    this.dataInputStream.read();
    byte[] bytes = new byte[len];
    this.dataInputStream.read(bytes, 0, len);
  }

  @After
  public void tearDown() throws Exception {
    this.dataOutputStream.write(new DisconnectMsg(name).toByteArray());
    this.socket.close();
    this.server.stopServer();
  }

  @Test
  public void testHandleDisconnectMessage() throws IOException {
    this.dataOutputStream.write(new DisconnectMsg(name).toByteArray());
    assertEquals(CommonConstants.CONNECT_RESPONSE, this.dataInputStream.readInt());
    this.dataInputStream.read();
    assertFalse(this.dataInputStream.readBoolean());
    this.dataInputStream.read();
    byte[] bytes = "You are no longer connected.".getBytes();
    assertEquals(bytes.length, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] read = new byte[bytes.length];
    this.dataInputStream.read(read, 0, read.length);
    assertArrayEquals(bytes, read);
  }

  @Test
  public void testHandleQueryConnectedUsers() throws IOException {
    this.dataOutputStream.write(new UserQuery(name).toByteArray());
    assertEquals(CommonConstants.QUERY_USER_RESPONSE, this.dataInputStream.readInt());
    this.dataInputStream.read();
    assertEquals(0, this.dataInputStream.readInt());
  }

  @Test
  public void testBroadCastMessage() throws IOException {
    this.dataOutputStream.write(new BroadcastMsg(name, "Hello".getBytes()).toByteArray());
    assertEquals(CommonConstants.BROADCAST_MESSAGE, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] bytes = name.getBytes();
    assertEquals(bytes.length, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] read = new byte[bytes.length];
    this.dataInputStream.read(read, 0, read.length);
    assertArrayEquals(bytes, read);
    this.dataInputStream.read();
    bytes = "Hello".getBytes();
    assertEquals(bytes.length, this.dataInputStream.readInt());
    this.dataInputStream.read();
    read = new byte[bytes.length];
    this.dataInputStream.read(read, 0, read.length);
    assertArrayEquals(bytes, read);
  }

  @Test
  public void testDirectMessage() throws IOException {
    this.dataOutputStream.write(new DirectMsg(name, name, "Hello".getBytes()).toByteArray());
    assertEquals(CommonConstants.DIRECT_MESSAGE, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] bytes = name.getBytes();
    assertEquals(bytes.length, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] read = new byte[bytes.length];
    this.dataInputStream.read(read, 0, read.length);
    assertArrayEquals(bytes, read);
    this.dataInputStream.read();
    assertEquals(bytes.length, this.dataInputStream.readInt());
    this.dataInputStream.read();
    read = new byte[bytes.length];
    this.dataInputStream.read(read, 0, read.length);
    assertArrayEquals(bytes, read);
    this.dataInputStream.read();
    bytes = "Hello".getBytes();
    assertEquals(bytes.length, this.dataInputStream.readInt());
    this.dataInputStream.read();
    read = new byte[bytes.length];
    this.dataInputStream.read(read, 0, read.length);
    assertArrayEquals(bytes, read);
  }

  @Test
  public void testFailedMessage() throws IOException {
    this.dataOutputStream.writeInt(675);
    this.dataOutputStream.write(CommonConstants.SPACE_BYTE);
    assertEquals(CommonConstants.FAILED_MESSAGE, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] bytes = "Undefined header.".getBytes();
    assertEquals(bytes.length, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] read = new byte[bytes.length];
    this.dataInputStream.read(read, 0, read.length);
    assertArrayEquals(bytes, read);
  }

  @Test
  public void testInsultMessage() throws IOException {
    this.dataOutputStream.write(new InsultMsg(name, "Steve").toByteArray());
    assertEquals(CommonConstants.FAILED_MESSAGE, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] bytes = "Invalid user or recipient".getBytes();
    assertEquals(bytes.length, this.dataInputStream.readInt());
    this.dataInputStream.read();
    byte[] read = new byte[bytes.length];
    this.dataInputStream.read(read, 0, read.length);
    assertArrayEquals(bytes, read);
  }

  @Test
  public void testEquals() throws IOException {
    ChatRoomServer server1 = new ChatRoomServer(54231);
    Socket socket = new Socket("127.0.0.1", port);
    Socket socket1 = new Socket("127.0.0.1", 54231);
    ClientHandler clientHandler1 = new ClientHandler(socket, new ServerMessageAgent(new HashMap<>()));
    assertEquals(clientHandler1, clientHandler1);
    assertNotEquals(clientHandler1, null);
    assertNotEquals(clientHandler1, new ArrayList<>());
    ClientHandler clientHandler2 = new ClientHandler(socket1, new ServerMessageAgent(new ConcurrentHashMap<>()));
    assertNotEquals(clientHandler1, clientHandler2);
  }

  @Test
  public void testHashCode() throws IOException {
    ChatRoomServer server1 = new ChatRoomServer(54231);
    Socket socket = new Socket("127.0.0.1", port);
    Socket socket1 = new Socket("127.0.0.1", 54231);
    ClientHandler clientHandler1 = new ClientHandler(socket, new ServerMessageAgent(new HashMap<>()));
    ClientHandler clientHandler2 = new ClientHandler(socket1, new ServerMessageAgent(new ConcurrentHashMap<>()));
    assertNotEquals(clientHandler1.hashCode(), clientHandler2.hashCode());
    assertEquals(clientHandler1.hashCode(), clientHandler1.hashCode());
  }
}