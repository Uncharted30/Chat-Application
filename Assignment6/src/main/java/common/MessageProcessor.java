package common;

import common.beans.BroadcastMsg;
import common.beans.DirectMsg;
import java.io.DataInputStream;
import java.io.IOException;

public abstract class MessageProcessor {
  private DataInputStream dataInputStream;

  public MessageProcessor(DataInputStream dataInputStream) {
    this.dataInputStream = dataInputStream;
  }

  protected String readString() throws IOException {
    int len = this.dataInputStream.readInt();
    this.dataInputStream.read();
    byte[] usernameBytes = new byte[len];
    this.dataInputStream.read(usernameBytes);
    return new String(usernameBytes, 0, len);
  }

  protected byte[] readByteArray() throws IOException {
    int len = this.dataInputStream.readInt();
    this.dataInputStream.read();
    byte[] result = new byte[len];
    this.dataInputStream.read(result, 0, len);
    return result;
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
}
