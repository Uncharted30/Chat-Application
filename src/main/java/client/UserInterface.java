package client;

import common.CommonConstants;
import common.beans.BroadcastMsg;
import common.beans.DirectMsg;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import common.beans.interfaces.ChatRoomProtocol;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * The type User interface.
 */
public class UserInterface {


  /**
   * whether the user has already put a command
   */
  private boolean hasCMD;
  /**
   * current cmd
   */
  private String currentCMD;
  /**
   * username
   */
  private String username;
  /**
   * standard in
   */
  private BufferedReader stdIn;

  /**
   * Instantiates a new User interface.
   *
   * @param username the username
   * @param stdIn the standard in
   */
  UserInterface(String username, BufferedReader stdIn) {
    this.username = username;
    this.stdIn = stdIn;
  }

  /**
   * Gets message.
   *
   * @return the message
   * @throws IOException the io exception
   */
  public ChatRoomProtocol getMessage() throws IOException {
    // get CMD first
    if (!this.hasCMD) {
      System.out.println("\nPlease Enter a command: ");
      String line = stdIn.readLine();
      this.currentCMD = line;
      this.hasCMD = true;
      return null;
      // handle CMD
    } else {
      return handleCommand();
    }
  }

  /**
   *
   * @return the msg given the command
   * @throws IOException IO Exception
   */
  private ChatRoomProtocol handleCommand() throws IOException {
    ChatRoomProtocol msg;
    switch (this.currentCMD) {
      case CommonConstants.HELP_CMD:
        handleHelpCommand();
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
        System.out.println("Type ? to see the usage");
        msg = null;
    }
    return msg;
  }

  /**
   * handle help cmd
   */
  private void handleHelpCommand() {
    System.out.println("Usage:");
    System.out.println("logoff: sends a DISCONNECT_MESSAGE to the server\n"
        + "who: sends a QUERY_CONNECTED_USERS to the server\n"
        + "@user: sends a DIRECT_MESSAGE to the specified user to the server\n"
        + "@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected\n"
        + "!user: sends a SEND_INSULT message to the server, to be sent to the specified user");
    hasCMD = false;
    currentCMD = "";
  }

  /**
   * handle log off cmd
   * @return disconnectMsg
   */
  private DisconnectMsg handleLogOffCommand() {
    hasCMD = false;
    currentCMD = "";
    return new DisconnectMsg(username);
  }

  /**
   * handle who cmd
   * @return UserQuery
   */
  private UserQuery handleShowUsersCommand() {
    hasCMD = false;
    currentCMD = "";
    return new UserQuery(username);
  }

  /**
   * handle @user cmd
   * @return direct msg
   * @throws IOException io exception
   */
  private DirectMsg handleSendMessageCommand() throws IOException {
    System.out.println("Please Enter the username you want to send the message to:");
    String receiver = stdIn.readLine();
    System.out.println("Please Enter the message you want to send:");
    String message = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return new DirectMsg(username, receiver, message.getBytes());
  }

  /**
   * handle @all cmd
   * @return broadcast msg
   * @throws IOException io exception
   */
  private BroadcastMsg handleBroadCastCommand() throws IOException {
    System.out.println("Please Enter the message you want to send:");
    String message = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return new BroadcastMsg(username, message.getBytes());
  }

  /**
   * handle !user cmd
   * @return insult msg
   * @throws IOException io exception
   */
  private InsultMsg handleSendInsultCommand() throws IOException {
    System.out.println("Please Enter the username you want to send the insult to:");
    String receiver = stdIn.readLine();
    hasCMD = false;
    currentCMD = "";
    return new InsultMsg(username, receiver);
  }
}
