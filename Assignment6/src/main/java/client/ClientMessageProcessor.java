package client;

import common.ConvertUtil;
import common.beans.BroadcastMsg;
import common.beans.ConnectRes;
import common.beans.DirectMsg;
import common.beans.QueryRes;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientMessageProcessor {
  private DataInputStream inputStream;

  public ClientMessageProcessor(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }

  private String readItem() throws IOException{
    int itemLen = inputStream.readInt();
    byte[] item = new byte[itemLen];
    inputStream.read();
    inputStream.read(item, 0, itemLen);
    return new String(item);
  }

  public ConnectRes processDisconnectResMsg() throws IOException {
    boolean status = ConvertUtil.byteToBoolean((byte)inputStream.read());
    inputStream.read();
    String content = readItem();
    return new ConnectRes(status, content);
  }

  public String processFailedMsg() throws IOException {
    return readItem();
  }

  public ConnectRes processConnectResMsg() throws IOException{
    boolean status = ConvertUtil.byteToBoolean((byte)inputStream.read());
    inputStream.read();
    String content = readItem();
    return new ConnectRes(status, content);
  }

  public DirectMsg processDirectMsg() throws IOException{
    String sender = readItem();
    inputStream.read();
    String recipient = readItem();
    inputStream.read();
    String content = readItem();
    return new DirectMsg(sender, recipient, content);
  }

  public BroadcastMsg processBroadcastMsg() throws IOException{
    String sender = readItem();
    inputStream.read();
    String content = readItem();
    return new BroadcastMsg(sender, content);
  }

  public QueryRes processQueryMsg() throws IOException{
    int userNum = inputStream.readInt();
    List<String> users = new ArrayList<>();
    for (int i = 0; i < userNum; i++) {
      inputStream.read();
      users.add(readItem());
    }
    return new QueryRes(users);
  }

  public DataInputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }
}
