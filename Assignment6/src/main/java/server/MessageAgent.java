package server;

import common.beans.DisconnectMsg;
import common.beans.Message;
import common.beans.QueryRes;
import common.beans.UserQuery;
import common.beans.interfaces.ChatRoomProtocol;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MessageAgent {

  private Map<String, ClientHandler> clients;

  public MessageAgent(Map<String, ClientHandler> clients) {
    this.clients = clients;
  }

  public boolean sendBroadcastMessage(Message message) throws IOException {
    if (this.clients.containsKey(message.getSender())) {
      for (ClientHandler clientHandler : this.clients.values()) {
        clientHandler.sendMessage(message.toByteArray());
      }
      return true;
    }
    return false;
  }

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

  public void disconnect(DisconnectMsg disconnectMsg) {
    this.clients.remove(disconnectMsg.getUsername());
  }
}