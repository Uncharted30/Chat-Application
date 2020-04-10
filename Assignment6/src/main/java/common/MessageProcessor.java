package common;

import java.io.DataInputStream;
import java.io.IOException;

public class MessageProcessor {
  private DataInputStream inputStream;

  public MessageProcessor(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }

  public String processLogOffMsg() throws IOException {
    int usernameLen = inputStream.readInt();
    byte[] usernameBytes = new byte[usernameLen];
    inputStream.read();
    inputStream.read(usernameBytes, 0, usernameLen);
    return new String(usernameBytes);
  }
  public DataInputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }
}
