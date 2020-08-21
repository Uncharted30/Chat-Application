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

/**
 * The type Client message processor.
 */
public class ClientMessageProcessor extends MessageProcessor {

  /**
   * data input stream
   */
  private DataInputStream inputStream;

  /**
   * constructor
   *
   * @param inputStream data input stream
   */
  public ClientMessageProcessor(DataInputStream inputStream) {
    super(inputStream);
    this.inputStream = inputStream;
  }

  /**
   * Process disconnect res msg.
   *
   * @return the connect res
   * @throws IOException the io exception
   */
  public ConnectRes processDisconnectResMsg() throws IOException {
    boolean status = ConvertUtil.byteToBoolean((byte)inputStream.read());
    inputStream.read();
    String content = readString();
    return new ConnectRes(status, content);
  }

  /**
   * Process failed msg.
   *
   * @return the failed msg
   * @throws IOException the io exception
   */
  public FailedMsg processFailedMsg() throws IOException {
    return new FailedMsg(this.readString());
  }

  /**
   * Process connect res msg.
   *
   * @return the connect res
   * @throws IOException the io exception
   */
  public ConnectRes processConnectResMsg() throws IOException{
    boolean status = ConvertUtil.byteToBoolean((byte)inputStream.read());
    inputStream.read();
    String content = readString();
    return new ConnectRes(status, content);
  }

  /**
   * Process query msg.
   *
   * @return the query res
   * @throws IOException the io exception
   */
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
