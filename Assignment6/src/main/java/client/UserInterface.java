package client;

import common.ConstantsUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface {
  /**
   * whether the user has already put a command
   */
  private static boolean hasCMD;
  private static String currentCMD;

  public static void main(String[] args) {
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      // get CMD first
      if (!hasCMD) {
        System.out.println("\nPlease Enter a command: ");
        try {
          String line = stdIn.readLine();
          getCommand(line);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      // handle CMD
      else {
        handleCommand();
      }
    }
  }

  public static void getCommand(String line) {
    // no such command
    if (!ConstantsUtil.CMD_SET.contains(line)) {
      System.out.println("Error! No Such Command!");
      System.out.println("Type ? to see the usage");
    } else {
      currentCMD = line;
      hasCMD = true;
    }
  }
  public static void handleCommand() {
    switch (currentCMD) {
      case ConstantsUtil.HELP_CMD:
        handleHelpCommand();
        break;
      case ConstantsUtil.LOG_OFF_CMD:
        handleLogOffCommand();
        break;
      case ConstantsUtil.SHOW_USERS_CMD:
        handleShowUsersCommand();
        break;
      case ConstantsUtil.SEND_MESSAGE_CMD:
        handleSendMessageCommand();
        break;
      case ConstantsUtil.BROADCAST_CMD:
        handleBroadCastCommand();
        break;
      case ConstantsUtil.SEND_INSULT_CMD:
        handleSendInsultCommand();
        break;
      default:
        System.out.println("Error! No Such Command!");
    }
  }

  public static void handleHelpCommand() {
    System.out.println("Usage:");
    System.out.println("logoff: sends a DISCONNECT_MESSAGE to the server\n"
        + "who: sends a QUERY_CONNECTED_USERS to the server\n"
        + "@user: sends a DIRECT_MESSAGE to the specified user to the server\n"
        + "@all: sends a BROADCAST_MESSAGE to the server, to be sent to all users connected\n"
        + "!user: sends a SEND_INSULT message to the server, to be sent to the specified user");
    hasCMD = false;
  }

  public static void handleLogOffCommand() {
    hasCMD = false;
  }
  public static void handleShowUsersCommand() {
    hasCMD = false;
  }
  public static void handleSendMessageCommand() {
    hasCMD = false;
  }
  public static void handleBroadCastCommand() {
    hasCMD = false;
  }
  public static void handleSendInsultCommand() {
    hasCMD = false;
  }
}
