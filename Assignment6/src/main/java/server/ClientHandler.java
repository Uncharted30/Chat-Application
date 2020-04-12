package server;

import common.CommonConstants;
import common.beans.BroadcastMsg;
import common.beans.ConnectRes;
import common.beans.DisconnectMsg;
import common.beans.FailedMsg;
import common.beans.UserQuery;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class ClientHandler implements Runnable {

  private Socket socket;
  private MessageAgent messageAgent;
  private Boolean connected;
  private DataInputStream dataInputStream;
  private DataOutputStream dataOutputStream;
  private ServerMessageProcessor messageProcessor;

  public ClientHandler(Socket socket, MessageAgent messageAgent) throws IOException {
    this.socket = socket;
    this.messageAgent = messageAgent;
    this.connected = true;
    this.dataInputStream = new DataInputStream(socket.getInputStream());
    this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    this.messageProcessor = new ServerMessageProcessor(this.dataInputStream);
  }

  private void listen() throws IOException {
    while (this.connected) {
      try {
        int header = this.dataInputStream.readInt();
        this.dataInputStream.read();
        switch (header) {
          case (CommonConstants.DISCONNECT_MESSAGE):
            handleDisconnectMsg();
            break;
          case (CommonConstants.QUERY_CONNECTED_USERS):
            handleUserQuery();
            break;
          case (CommonConstants.BROADCAST_MESSAGE):
            handleBroadcastMessage();
            break;
          case (CommonConstants.DIRECT_MESSAGE):
            System.out.println("Direct!");
            break;
          case (CommonConstants.SEND_INSULT):
            System.out.println("INSULT!");
            break;
          default:
            System.out.println("Wrong header!");
        }
      } catch (SocketTimeoutException e) {
//        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.socket.close();
  }

  public void disconnect() {
    this.connected = false;
  }

  public void sendMessage(byte[] message) throws IOException {
    this.dataOutputStream.write(message);
    this.dataOutputStream.flush();
    System.out.println(Arrays.toString(message));
  }

  private void handleDisconnectMsg() {
    try {
      DisconnectMsg disconnectMsg = this.messageProcessor.processDisconnectMsg();
      this.messageAgent.disconnect(disconnectMsg);
      this.disconnect();
      this.sendMessage(new ConnectRes("You are no longer connected.").toByteArray());
    } catch (IOException e) {
      FailedMsg failedMsg = new FailedMsg(
          "Exception occurred during disconnecting you from the server, please try again. " + e
              .getMessage());
      try {
        this.sendMessage(failedMsg.toByteArray());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private void handleUserQuery() {
    try {
      UserQuery userQuery = this.messageProcessor.processUserQuery();
      this.messageAgent.sendUserList(userQuery);
    } catch (IOException e) {
      e.printStackTrace();
      FailedMsg failedMsg = new FailedMsg(
          "Exception occurred during handling the request. " + e.getMessage());
      try {
        this.sendMessage(failedMsg.toByteArray());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  private void handleBroadcastMessage() {
    try {
      BroadcastMsg broadcastMsg = this.messageProcessor.processBroadcastMsg();
      this.messageAgent.sendBroadcastMessage(broadcastMsg);
    } catch (IOException e) {
      FailedMsg failedMsg = new FailedMsg(
          "Exception occurred when sending the message. " + e.getMessage());
      try {
        this.sendMessage(failedMsg.toByteArray());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  @Override
  public void run() {
    try {
      this.listen();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
