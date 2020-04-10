package client;

import common.ConstantsUtil;
import common.MessageConstuctor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class UserInterface {
  /**
   * whether the user has already put a command
   */
  private  boolean hasCMD;
  private  String currentCMD;
  private  String username;
  private BufferedReader stdIn;

  UserInterface(String username, BufferedReader stdIn) {
    this.username = username;
    this.stdIn = stdIn;
  }

  public byte[] getMessage() throws IOException {
      // get CMD first
      if (!this.hasCMD) {
        System.out.println("\nPlease Enter a command: ");
        String line = stdIn.readLine();
        getCommand(line);
        return new byte[0];
      }
      // handle CMD
      else {
        return handleCommand();
      }
  }

  private void getCommand(String line) {
    // no such command
    if (!ConstantsUtil.CMD_SET.contains(line)) {
      System.out.println("Error! No Such Command!");
      System.out.println("Type ? to see the usage");
    } else {
      this.currentCMD = line;
      this.hasCMD = true;
    }
  }
  private byte[] handleCommand() throws IOException {
    byte[] msg;
    switch (this.currentCMD) {
      case ConstantsUtil.HELP_CMD:
        msg = handleHelpCommand();
        break;
      case ConstantsUtil.LOG_OFF_CMD:
        msg = handleLogOffCommand();
        break;
      case ConstantsUtil.SHOW_USERS_CMD:
        msg =  handleShowUsersCommand();
        break;
      case ConstantsUtil.SEND_MESSAGE_CMD:
        msg = handleSendMessageCommand();
        break;
      case ConstantsUtil.BROADCAST_CMD:
        msg = handleBroadCastCommand();
        break;
      case ConstantsUtil.SEND_INSULT_CMD:
        msg = handleSendInsultCommand();
        break;
      default:
        System.out.println("Error! No Such Command!");
        msg = new byte[0];
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

  private byte[] handleLogOffCommand() {
    hasCMD = false;
    currentCMD = "";
    return MessageConstuctor.getTypeOneMsg(Arrays.asList(username), ConstantsUtil.DISCONNECT_MESSAGE);
  }
  private byte[] handleShowUsersCommand() {
    hasCMD = false;
    currentCMD = "";
    return MessageConstuctor.getTypeOneMsg(Arrays.asList(username), ConstantsUtil.QUERY_CONNECTED_USERS);
  }

  private byte[] handleSendMessageCommand() throws IOException {
    System.out.println("Please Enter the username you want to send the message to:");
    String receiver = stdIn.readLine();
    System.out.println("Please Enter the message you want to send:");
    String message = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return MessageConstuctor.getTypeOneMsg(Arrays.asList(username, receiver, message), ConstantsUtil.DIRECT_MESSAGE);
  }
  private byte[] handleBroadCastCommand() throws IOException {
    System.out.println("Please Enter the message you want to send:");
    String message = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return MessageConstuctor.getTypeOneMsg(Arrays.asList(username, message), ConstantsUtil.BROADCAST_MESSAGE);
  }
  private byte[] handleSendInsultCommand() throws IOException {
    System.out.println("Please Enter the username you want to send the insult to:");
    String receiver = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return MessageConstuctor.getTypeOneMsg(Arrays.asList(username, receiver), ConstantsUtil.SEND_INSULT);
  }
}
