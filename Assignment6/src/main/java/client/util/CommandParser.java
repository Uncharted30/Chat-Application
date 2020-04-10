package client.util;

import common.ConstantsUtil;
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
    Option option1 = new Option(ConstantsUtil.IP_OPTION, true, "ip address of the server that client want to connect to");
    Option option2 = new Option(ConstantsUtil.PORT_OPTION, true,
        "port of the server that client want to connect to");
    Option option3 = new Option(ConstantsUtil.USERNAME_OPTION, true, "username of the client");
    Options options = new Options();
    options.addOption(option1);
    options.addOption(option2);
    options.addOption(option3);
    CommandLineParser parser = new DefaultParser();
    CommandLine commands = parser.parse(options, args);

    Map<String, String> map = new HashMap<>();

    if (commands.hasOption(ConstantsUtil.IP_OPTION)) {
      map.put(ConstantsUtil.IP_OPTION, commands.getOptionValue(ConstantsUtil.IP_OPTION));
    } else {
      map.put(ConstantsUtil.IP_OPTION, ConstantsUtil.DEFAULT_SERVER_IP);
    }

    if (commands.hasOption(ConstantsUtil.PORT_OPTION)) {
      map.put(ConstantsUtil.PORT_OPTION,
          commands.getOptionValue(ConstantsUtil.PORT_OPTION));
    } else {
      throw new ParseException("No port of the server to be specified");
    }

    if (commands.hasOption(ConstantsUtil.USERNAME_OPTION)) {
      map.put(ConstantsUtil.USERNAME_OPTION,
          commands.getOptionValue(ConstantsUtil.USERNAME_OPTION));
    } else {
      throw new ParseException("No username of the client to be specified");
    }

    return map;
  }
}

