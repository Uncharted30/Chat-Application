package server;

import common.MessageProcessor;
import common.beans.BroadcastMsg;
import common.beans.ConnectMsg;
import common.beans.DirectMsg;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;


public class ServerMessageProcessor extends MessageProcessor {

  private DataInputStream dataInputStream;

  public ServerMessageProcessor(DataInputStream dataInputStream) {
    super(dataInputStream);
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

  public InsultMsg processInsultMsg() throws IOException {
    String sender = this.readString();
    this.dataInputStream.read();
    String recipient = this.readString();
    return new InsultMsg(sender, recipient);
  }
}
