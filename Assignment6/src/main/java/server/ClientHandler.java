package server;

import common.CommonConstants;
import common.beans.BroadcastMsg;
import common.beans.ConnectRes;
import common.beans.DirectMsg;
import common.beans.DisconnectMsg;
import common.beans.FailedMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Objects;

/**
 * The type Client handler, handles clients requests in separate threads.
 */
public class ClientHandler implements Runnable {

  private Socket socket;
  private ServerMessageAgent serverMessageAgent;
  private Boolean connected;
  private DataInputStream dataInputStream;
  private DataOutputStream dataOutputStream;
  private ServerMessageProcessor messageProcessor;

  /**
   * Instantiates a new Client handler.
   *
   * @param socket             the socket of the client
   * @param serverMessageAgent the server message agent
   * @throws IOException the io exception
   */
  public ClientHandler(Socket socket, ServerMessageAgent serverMessageAgent) throws IOException {
    this.socket = socket;
    this.serverMessageAgent = serverMessageAgent;
    this.connected = true;
    this.dataInputStream = new DataInputStream(socket.getInputStream());
    this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
    this.messageProcessor = new ServerMessageProcessor(this.dataInputStream);
  }

  /**
   * Start listening clients' request.
   *
   * @throws IOException the io exception
   */
  private void listen() throws IOException {
    while (this.connected) {
      try {
        int header = this.dataInputStream.readInt();
        this.dataInputStream.read();
        switch (header) {
          case (CommonConstants.DISCONNECT_MESSAGE):
            this.handleDisconnectMsg();
            break;
          case (CommonConstants.QUERY_CONNECTED_USERS):
            this.handleUserQuery();
            break;
          case (CommonConstants.BROADCAST_MESSAGE):
            this.handleBroadcastMessage();
            break;
          case (CommonConstants.DIRECT_MESSAGE):
            this.handleDirectMessage();
            break;
          case (CommonConstants.SEND_INSULT):
            this.handleInsultMessage();
            break;
          default:
            this.sendFailedMessage("Undefined header.");
        }
      } catch (SocketTimeoutException e) {
//        e.printStackTrace();
      } catch (EOFException e) {
        if (this.connected) {
          this.disconnect();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.socket.close();
  }

  /**
   * Disconnect the client from the server.
   *
   * @throws IOException the io exception
   */
  public void disconnect() throws IOException {
    this.sendMessage(new ConnectRes(false, "You are no longer connected.").toByteArray());
    this.connected = false;
  }

  /**
   * Send message to the client.
   *
   * @param message the message
   * @throws IOException the io exception
   */
  public void sendMessage(byte[] message) throws IOException {
    this.dataOutputStream.write(message);
    System.out.println(Arrays.toString(message));
  }

  /**
   * Handles disconnect request, send client response and stop the thread.
   */
  private void handleDisconnectMsg() {
    try {
      DisconnectMsg disconnectMsg = this.messageProcessor.processDisconnectMsg();
      this.serverMessageAgent.disconnect(disconnectMsg);
      this.disconnect();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      this.sendFailedMessage(
          "Exception occurred during disconnecting you from the server, please try again. " + e
              .getMessage());
    }
  }

  /**
   * Handles user query request, send user connected user list.
   */
  private void handleUserQuery() {
    try {
      UserQuery userQuery = this.messageProcessor.processUserQuery();
      boolean res = this.serverMessageAgent.sendUserList(userQuery);
      if (!res) {
        this.sendFailedMessage("You are not connected.");
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      this.sendFailedMessage("Exception occurred during handling the request. " + e.getMessage());
    }
  }

  /**
   * Handles broadcast message sending request, send the message to every client that connected to
   * the server.
   */
  private void handleBroadcastMessage() {
    try {
      BroadcastMsg broadcastMsg = this.messageProcessor.processBroadcastMsg();
      boolean res = this.serverMessageAgent.sendBroadcastMessage(broadcastMsg);
      if (!res) {
        this.sendFailedMessage("Invalid sender!");
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      this.sendFailedMessage("Exception occurred when sending the message. " + e.getMessage());
    }
  }

  /**
   * Handles direct message sending request, send the message to the recipient.
   */
  private void handleDirectMessage() {
    try {
      DirectMsg directMsg = this.messageProcessor.processDirectMsg();
      boolean res = this.serverMessageAgent.sendDirectMessage(directMsg);
      if (!res) {
        this.sendFailedMessage("Invalid sender or recipient.");
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      this.sendFailedMessage("Exception occurred when sending the message. " + e.getMessage());
    }
  }

  /**
   * Sends a failed message to the client.
   *
   * @param message the failed message to be sent.
   */
  private void sendFailedMessage(String message) {
    FailedMsg failedMsg = new FailedMsg(message);
    try {
      this.sendMessage(failedMsg.toByteArray());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Handles insult message sending request, randomly generates a insult sentence and send it.
   */
  private void handleInsultMessage() {
    try {
      InsultMsg insultMsg = this.messageProcessor.processInsultMsg();
      boolean res = this.serverMessageAgent.sendInsult(insultMsg);
      if (!res) {
        this.sendFailedMessage("Invalid user or recipient");
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
      this.sendFailedMessage("Exception occurred when sending the message. " + e.getMessage());
    }
  }

  /**
   * Start running the thread.
   */
  @Override
  public void run() {
    try {
      this.listen();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientHandler that = (ClientHandler) o;
    return socket.equals(that.socket) &&
        serverMessageAgent.equals(that.serverMessageAgent) &&
        connected.equals(that.connected) &&
        dataInputStream.equals(that.dataInputStream) &&
        dataOutputStream.equals(that.dataOutputStream) &&
        messageProcessor.equals(that.messageProcessor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(socket, serverMessageAgent, connected, dataInputStream, dataOutputStream,
        messageProcessor);
  }
}
