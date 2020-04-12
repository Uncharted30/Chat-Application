package server;

import common.beans.DisconnectMsg;
import common.beans.Message;
import common.beans.QueryRes;
import common.beans.UserQuery;
import common.beans.interfaces.ChatRoomProtocol;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MessageAgent {

  private Map<String, ClientHandler> clients;

  public MessageAgent(Map<String, ClientHandler> clients) {
    this.clients = clients;
  }

  public void sendBroadcastMessage(ChatRoomProtocol message) throws IOException {
    for (ClientHandler clientHandler : this.clients.values()) {
      clientHandler.sendMessage(message.toByteArray());
    }
  }

  public void sendDirectMessage(Message message) throws IOException {
    this.clients.get(message.getRecipient()).sendMessage(message.toByteArray());
  }

  public void sendUserList(UserQuery userQuery) throws IOException {
    this.clients.get(userQuery.getUsername())
        .sendMessage(new QueryRes(new ArrayList<>(this.clients.keySet())).toByteArray());
  }

  public void disconnect(DisconnectMsg disconnectMsg) {
    this.clients.remove(disconnectMsg.getUsername());
  }
}