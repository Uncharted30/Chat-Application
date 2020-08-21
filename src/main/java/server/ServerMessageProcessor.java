package server;

import common.MessageProcessor;
import common.beans.BroadcastMsg;
import common.beans.DirectMsg;
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
    DisconnectMsg disconnectMsg = new DisconnectMsg(this.readString());
    System.out.println(disconnectMsg.getMessage());
    return disconnectMsg;
  }

  /**
   * Process user query, read user query request from input stream.
   *
   * @return the user query bean
   * @throws IOException the io exception
   */
  public UserQuery processUserQuery() throws IOException {
    UserQuery userQuery = new UserQuery(this.readString());
    System.out.println(userQuery.getMessage());
    return userQuery;
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
    InsultMsg insultMsg = new InsultMsg(sender, recipient);
    System.out.println(insultMsg.getMessage());
    return insultMsg;
  }

  /**
   * Process broadcast message, reads a broadcast message from input stream.
   *
   * @return the broadcast msg
   * @throws IOException the io exception
   */
  @Override
  public BroadcastMsg processBroadcastMsg() throws IOException {
    BroadcastMsg broadcastMsg = super.processBroadcastMsg();
    System.out.println(broadcastMsg.getMessage());
    return broadcastMsg;
  }

  /**
   * Process direct message, reads a direct message from input stream.
   *
   * @return the direct msg
   * @throws IOException the io exception
   */
  @Override
  public DirectMsg processDirectMsg() throws IOException {
    DirectMsg directMsg = super.processDirectMsg();
    System.out.println("To " + directMsg.getRecipient() + ": " + directMsg.getMessage());
    return directMsg;
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
