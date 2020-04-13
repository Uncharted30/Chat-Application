package server;

import common.MessageProcessor;
import common.beans.ConnectMsg;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;


/**
 * The type Server message processor.
 */
public class ServerMessageProcessor extends MessageProcessor {

  private DataInputStream dataInputStream;

  /**
   * Instantiates a new Server message processor.
   *
   * @param dataInputStream the data input stream
   */
  public ServerMessageProcessor(DataInputStream dataInputStream) {
    super(dataInputStream);
    this.dataInputStream = dataInputStream;
  }

  /**
   * Process disconnect message, read disconnect request from input stream.
   *
   * @return the disconnect msg bean
   * @throws IOException the io exception
   */
  public DisconnectMsg processDisconnectMsg() throws IOException {
    return new DisconnectMsg(this.readString());
  }

  /**
   * Process user query, read user query request from input stream.
   *
   * @return the user query bean
   * @throws IOException the io exception
   */
  public UserQuery processUserQuery() throws IOException {
    return new UserQuery(this.readString());
  }

  /**
   * Process insult message, read an insult message from input stream.
   *
   * @return the insult msg bean
   * @throws IOException the io exception
   */
  public InsultMsg processInsultMsg() throws IOException {
    String sender = this.readString();
    this.dataInputStream.read();
    String recipient = this.readString();
    return new InsultMsg(sender, recipient);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerMessageProcessor that = (ServerMessageProcessor) o;
    return dataInputStream.equals(that.dataInputStream);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataInputStream);
  }
}
