package client;

import common.CommonConstants;
import common.MessageConstuctor;
import common.beans.BroadcastMsg;
import common.beans.DirectMsg;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import common.beans.interfaces.ChatRoomProtocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class UserInterface {

  /**
   * whether the user has already put a command
   */
  private boolean hasCMD;
  private String currentCMD;
  private String username;
  private BufferedReader stdIn;

  UserInterface(String username, BufferedReader stdIn) {
    this.username = username;
    this.stdIn = stdIn;
  }

  public ChatRoomProtocol getMessage() throws IOException {
    // get CMD first
    if (!this.hasCMD) {
      System.out.println("\nPlease Enter a command: ");
      String line = stdIn.readLine();
      getCommand(line);
      return null;
    } else {
      return handleCommand();
    }
  }

  private void getCommand(String line) {
    // no such command
    if (!CommonConstants.CMD_SET.contains(line)) {
      System.out.println("Error! No Such Command!");
      System.out.println("Type ? to see the usage");
    } else {
      this.currentCMD = line;
      this.hasCMD = true;
    }
  }

  private ChatRoomProtocol handleCommand() throws IOException {
    ChatRoomProtocol msg;
    switch (this.currentCMD) {
      case CommonConstants.HELP_CMD:
        msg = null;
        break;
      case CommonConstants.LOG_OFF_CMD:
        msg = handleLogOffCommand();
        break;
      case CommonConstants.SHOW_USERS_CMD:
        msg = handleShowUsersCommand();
        break;
      case CommonConstants.SEND_MESSAGE_CMD:
        msg = handleSendMessageCommand();
        break;
      case CommonConstants.BROADCAST_CMD:
        msg = handleBroadCastCommand();
        break;
      case CommonConstants.SEND_INSULT_CMD:
        msg = handleSendInsultCommand();
        break;
      default:
        System.out.println("Error! No Such Command!");
        msg = null;
    }
    return msg;
  }

  private byte[] handleHelpCommand() {
    System.out.println("Usage:");
    System.out.println("logoff: sends a DISCONNECT_MESSAGE to the server\n"
        + "who: sends a QUERY_CONNECTED_USERS to the server\n"
        + "@user: sends a DIRECT_MESSAGE to the specified user to the server\n"
        + "@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected\n"
        + "!user: sends a SEND_INSULT message to the server, to be sent to the specified user");
    hasCMD = false;
    currentCMD = "";
    return new byte[0];
  }

  private DisconnectMsg handleLogOffCommand() {
    hasCMD = false;
    currentCMD = "";
    return new DisconnectMsg(username);
  }

  private UserQuery handleShowUsersCommand() {
    hasCMD = false;
    currentCMD = "";
    return new UserQuery(username);
  }

  private DirectMsg handleSendMessageCommand() throws IOException {
    System.out.println("Please Enter the username you want to send the message to:");
    String receiver = stdIn.readLine();
    System.out.println("Please Enter the message you want to send:");
    String message = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return new DirectMsg(username, receiver, message.getBytes());
  }

  private BroadcastMsg handleBroadCastCommand() throws IOException {
    System.out.println("Please Enter the message you want to send:");
    String message = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return new BroadcastMsg(username, message.getBytes());
  }

  private InsultMsg handleSendInsultCommand() throws IOException {
    System.out.println("Please Enter the username you want to send the insult to:");
    String receiver = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return new InsultMsg(username, receiver);
  }
}
