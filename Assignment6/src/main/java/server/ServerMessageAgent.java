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
import org.json.simple.parser.ParseException;
import server.interfaces.MessageAgent;

public class ServerMessageAgent implements MessageAgent {

  private Map<String, ClientHandler> clients;
  private RandomSentenceGenerator generator;

  public ServerMessageAgent(Map<String, ClientHandler> clients) {
    this.clients = clients;
    try {
      this.generator = new RandomSentenceGenerator(CommonConstants.GRAMMAR_DIR);
    } catch (IOException | ParseException e) {
//      e.printStackTrace();
      System.err.println("Error: Failed to initialize random sentence generator.");
    }
  }

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

  @Override
  public boolean sendDirectMessage(Message message) throws IOException {
    if (this.clients.containsKey(message.getSender()) && this.clients
        .containsKey(message.getRecipient())) {
      this.clients.get(message.getRecipient()).sendMessage(message.toByteArray());
      return true;
    }
    return false;
  }

  public boolean sendUserList(UserQuery userQuery) throws IOException {
    if (this.clients.containsKey(userQuery.getUsername())) {
      List<String> users = new LinkedList<>(this.clients.keySet());
      users.remove(userQuery.getUsername());
      this.clients.get(userQuery.getUsername()).sendMessage(new QueryRes(users).toByteArray());
      return true;
    }
    return false;
  }

  public boolean sendInsult(InsultMsg insultMsg) throws IOException {
    if (this.clients.containsKey(insultMsg.getSender()) && this.clients
        .containsKey(insultMsg.getRecipient())) {
      String insult = this.generator.generate(CommonConstants.INSULT_TITLE);
      insult = "[An insult to " + insultMsg.getRecipient() + "] " + insult;
      return this.sendBroadcastMessage(new BroadcastMsg(insultMsg.getSender(), insult.getBytes()));
    }
    return false;
  }

  public void disconnect(DisconnectMsg disconnectMsg) {
    this.clients.remove(disconnectMsg.getUsername());
  }
}