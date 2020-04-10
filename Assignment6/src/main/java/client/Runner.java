package client;

import client.util.CommandParser;
import common.ConstantsUtil;
import java.io.IOException;
import java.util.Map;
import org.apache.commons.cli.ParseException;

public class Runner {

  public static void main(String[] args) {
    try {
      Map<String, String> commands = CommandParser.parse(args);
      int port = Integer.parseInt(commands.get(ConstantsUtil.PORT_OPTION));
      Client client = new Client(commands.get(ConstantsUtil.IP_OPTION), port, commands.get(ConstantsUtil.USERNAME_OPTION));
      client.run();
    } catch (ParseException e) {
      System.err.println("Error! Failed to parse command, please check your input.");
      System.err.println("Arguments: \n"
          + "-i(localhost is the default val) the ip address of the server you want to connect\n"
          + "-p(must be specified) the port of the server you want to connect\n"
          + "-u(must be specified) the username");
    } catch (NumberFormatException e) {
      System.err.println("Error! port must be an integer");
    } catch (IOException e) {
      System.out.println("Some thing wrong to close socket!");
      e.printStackTrace();
    }
  }
}
