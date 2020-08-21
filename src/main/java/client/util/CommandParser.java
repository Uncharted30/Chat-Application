package client.util;

import common.CommonConstants;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandParser {

  /**
   * parse user input
   *
   * @param args the input of the users
   * @return the parse result which is a map
   * @throws ParseException when user's input is wrong
   */
  public static Map<String, String> parse(String[] args) throws ParseException {
    Option option1 = new Option(CommonConstants.IP_OPTION, true, "ip address of the server that client want to connect to");
    Option option2 = new Option(CommonConstants.PORT_OPTION, true,
        "port of the server that client want to connect to");
    Option option3 = new Option(CommonConstants.USERNAME_OPTION, true, "username of the client");
    Options options = new Options();
    options.addOption(option1);
    options.addOption(option2);
    options.addOption(option3);
    CommandLineParser parser = new DefaultParser();
    CommandLine commands = parser.parse(options, args);

    Map<String, String> map = new HashMap<>();

    if (commands.hasOption(CommonConstants.IP_OPTION)) {
      map.put(CommonConstants.IP_OPTION, commands.getOptionValue(CommonConstants.IP_OPTION));
    } else {
      map.put(CommonConstants.IP_OPTION, CommonConstants.DEFAULT_SERVER_IP);
    }

    if (commands.hasOption(CommonConstants.PORT_OPTION)) {
      map.put(CommonConstants.PORT_OPTION,
          commands.getOptionValue(CommonConstants.PORT_OPTION));
    } else {
      throw new ParseException("No port of the server to be specified");
    }

    if (commands.hasOption(CommonConstants.USERNAME_OPTION)) {
      map.put(CommonConstants.USERNAME_OPTION,
          commands.getOptionValue(CommonConstants.USERNAME_OPTION));
    } else {
      throw new ParseException("No username of the client to be specified");
    }

    return map;
  }
}

