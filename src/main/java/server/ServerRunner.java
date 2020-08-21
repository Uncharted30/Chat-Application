package server;

import common.CommonConstants;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.cli.ParseException;
import server.utils.ServerCommandParser;

public class ServerRunner {

  public static void main(String[] args) {
    try {
      Map<String, String> commands = ServerCommandParser.parseCommand(args);
      String portString = commands.getOrDefault(CommonConstants.PORT_OPTION, "0");
      int port = Integer.parseInt(portString);
      ChatRoomServer chatRoomServer = new ChatRoomServer(port);
      chatRoomServer.startServer();
      String command = "";
      Scanner in = new Scanner(System.in);
      while (!"exit".equals(command)) {
        command = in.nextLine();
      }
      chatRoomServer.stopServer();
    } catch (ParseException e) {
      System.err.println("Error: failed to parse command, please check your command.");
//      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Error: failed to create a server, please try again.");
    }
  }
}
