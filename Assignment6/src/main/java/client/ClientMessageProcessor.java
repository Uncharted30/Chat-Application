package client;

import common.utils.ConvertUtil;
import common.MessageProcessor;
import common.beans.ConnectRes;
import common.beans.FailedMsg;
import common.beans.QueryRes;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientMessageProcessor extends MessageProcessor {

  private DataInputStream inputStream;

  public ClientMessageProcessor(DataInputStream inputStream) {
    super(inputStream);
    this.inputStream = inputStream;
  }

  public ConnectRes processDisconnectResMsg() throws IOException {
    boolean status = ConvertUtil.byteToBoolean((byte)inputStream.read());
    inputStream.read();
    String content = readString();
    return new ConnectRes(status, content);
  }

  public FailedMsg processFailedMsg() throws IOException {
    return new FailedMsg(this.readString());
  }

  public ConnectRes processConnectResMsg() throws IOException{
    boolean status = ConvertUtil.byteToBoolean((byte)inputStream.read());
    inputStream.read();
    String content = readString();
    return new ConnectRes(status, content);
  }

  public QueryRes processQueryMsg() throws IOException{
    int userNum = inputStream.readInt();
    List<String> users = new ArrayList<>();
    for (int i = 0; i < userNum; i++) {
      this.inputStream.read();
      users.add(readString());
    }
    return new QueryRes(users);
  }
}
