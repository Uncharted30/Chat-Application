package server;

import assignment4.RandomSentenceGenerator;
import common.CommonConstants;
import common.beans.BroadcastMsg;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.Message;
import common.beans.QueryRes;
import common.beans.UserQuery;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.json.simple.parser.ParseException;
import server.interfaces.MessageAgent;

/**
 * The type Server message agent, client handler threads send message to other clients through
 * message agent.
 */
public class ServerMessageAgent implements MessageAgent {

  private Map<String, ClientHandler> clients;
  private RandomSentenceGenerator generator;

  /**
   * Instantiates a new Server message agent.
   *
   * @param clients the clients map
   */
  public ServerMessageAgent(Map<String, ClientHandler> clients) {
    this.clients = clients;
    try {
      this.generator = new RandomSentenceGenerator(CommonConstants.GRAMMAR_DIR);
    } catch (IOException | ParseException e) {
//      e.printStackTrace();
      System.err.println("Error: Failed to initialize random sentence generator.");
    }
  }

  /**
   * Send broadcast message to every client that is connected to the server.
   *
   * @param message the message to be sent
   * @return true if the agent sent message successfully
   * @throws IOException the io exception
   */
  @Override
  public boolean sendBroadcastMessage(Message message) throws IOException {
    if (this.clients.containsKey(message.getSender())) {
      for (ClientHandler clientHandler : this.clients.values()) {
        clientHandler.sendMessage(message.toByteArray());
      }
      return true;
    }
    return false;
  }

  /**
   * Send direct message to one specific client.
   *
   * @param message the message to be sent
   * @return true if the agent sent message successfully
   * @throws IOException the io exception
   */
  @Override
  public boolean sendDirectMessage(Message message) throws IOException {
    if (this.clients.containsKey(message.getSender()) && this.clients
        .containsKey(message.getRecipient())) {
      this.clients.get(message.getRecipient()).sendMessage(message.toByteArray());
      return true;
    }
    return false;
  }

  /**
   * Send connected user list to one client.
   *
   * @param userQuery the user query request bean
   * @return true if the agent sent message successfully
   * @throws IOException the io exception
   */
  public boolean sendUserList(UserQuery userQuery) throws IOException {
    if (this.clients.containsKey(userQuery.getUsername())) {
      List<String> users = new LinkedList<>(this.clients.keySet());
      users.remove(userQuery.getUsername());
      this.clients.get(userQuery.getUsername()).sendMessage(new QueryRes(users).toByteArray());
      return true;
    }
    return false;
  }

  /**
   * Send insult to one connected use.
   *
   * @param insultMsg the insult message request
   * @return true if the agent sent message successfully
   * @throws IOException the io exception
   */
  public boolean sendInsult(InsultMsg insultMsg) throws IOException {
    if (this.clients.containsKey(insultMsg.getSender()) && this.clients
        .containsKey(insultMsg.getRecipient())) {
      String insult = this.generator.generate(CommonConstants.INSULT_TITLE);
      insult = "[An insult to " + insultMsg.getRecipient() + "] " + insult;
      return this.sendBroadcastMessage(new BroadcastMsg(insultMsg.getSender(), insult.getBytes()));
    }
    return false;
  }

  /**
   * Disconnect user from the server.
   *
   * @param disconnectMsg the disconnect message bean
   */
  public void disconnect(DisconnectMsg disconnectMsg) {
    this.clients.remove(disconnectMsg.getUsername());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServerMessageAgent that = (ServerMessageAgent) o;
    return clients.equals(that.clients) &&
        generator.equals(that.generator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clients, generator);
  }
}