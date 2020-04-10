package common;

import client.bean.ConnectMsgRes;
import client.bean.DirectMsg;
import client.bean.QueryRes;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageProcessor {
  private DataInputStream inputStream;

  public MessageProcessor(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }

  private String readItem() throws IOException{
    int itemLen = inputStream.readInt();
    byte[] item = new byte[itemLen];
    inputStream.read();
    inputStream.read(item, 0, itemLen);
    return new String(item);
  }

  public String processLogOffMsg() throws IOException {
    return readItem();
  }

  public String processFailedMsg() throws IOException {
    return readItem();
  }

  public ConnectMsgRes processConnectResMsg() throws IOException{
    boolean status = ConvertUtil.byteToBoolean((byte)inputStream.read());
    inputStream.read();
    String content = readItem();
    return new ConnectMsgRes(status, content);
  }

  public DirectMsg processDirectMsg() throws IOException{
    String sender = readItem();
    inputStream.read();
    String recipient = readItem();
    inputStream.read();
    String content = readItem();
    return new DirectMsg(sender, recipient, content);
  }

  public QueryRes processQueryMsg() throws IOException{
    int userNum = inputStream.readInt();
    List<String> usernames = new ArrayList<>();
    for (int i = 0; i < userNum - 1; i++) {
      usernames.add(readItem());
      inputStream.read();
    }
    usernames.add(readItem());
    return new QueryRes(usernames);
  }

  public DataInputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(DataInputStream inputStream) {
    this.inputStream = inputStream;
  }
}
