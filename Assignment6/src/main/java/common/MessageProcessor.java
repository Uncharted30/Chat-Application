package common;

import client.bean.ConnectMsgRes;
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

  public ConnectMsgRes processConnectResMsg() throws IOException{
    boolean status = ConvertUtil.byteToBoolean((byte)inputStream.read());
    inputStream.read();
    int contentLen = inputStream.readInt();
    inputStream.read();
    byte[] contentBytes = new byte[contentLen];
    inputStream.read(contentBytes, 0, contentLen);
    return new ConnectMsgRes(status, new String(contentBytes));
  }
  public DataInputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }
}
