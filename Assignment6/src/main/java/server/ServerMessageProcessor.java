package server;

import common.beans.BroadcastMsg;
import common.beans.ConnectMsg;
import common.beans.DirectMsg;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import java.io.DataInputStream;
import java.io.IOException;


public class ServerMessageProcessor {

  private DataInputStream dataInputStream;

  public ServerMessageProcessor(DataInputStream dataInputStream) {
    this.dataInputStream = dataInputStream;
  }

  public ConnectMsg processConnectMsg() throws IOException {
    return new ConnectMsg(this.readString());
  }

  public DisconnectMsg processDisconnectMsg() throws IOException {
    return new DisconnectMsg(this.readString());
  }

  public UserQuery processUserQuery() throws IOException {
    return new UserQuery(this.readString());
  }

  public BroadcastMsg processBroadcastMsg() throws IOException {
    String sender = this.readString();
    this.dataInputStream.read();
    byte[] content = this.readByteArray();
    return new BroadcastMsg(sender, content);
  }

  public DirectMsg processDirectMsg() throws IOException {
    String sender = this.readString();
    this.dataInputStream.read();
    String recipient = this.readString();
    this.dataInputStream.read();
    byte[] content = this.readByteArray();
    return new DirectMsg(sender, recipient, content);
  }

  public InsultMsg processInsultMsg() throws IOException {
    String sender = this.readString();
    this.dataInputStream.read();
    String recipient = this.readString();
    return new InsultMsg(sender, recipient);
  }

  private String readString() throws IOException {
    int len = this.dataInputStream.readInt();
    this.dataInputStream.read();
    byte[] usernameBytes = new byte[len];
    this.dataInputStream.read(usernameBytes);
    return new String(usernameBytes, 0, len);
  }

  private byte[] readByteArray() throws IOException {
    int len = this.dataInputStream.readInt();
    this.dataInputStream.read();
    byte[] result = new byte[len];
    this.dataInputStream.read(result, 0, len);
    return result;
  }
}
