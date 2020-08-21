package server.utils;

import common.CommonConstants;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ServerCommandParser {
  public static Map<String, String> parseCommand(String[] args) throws ParseException {
    Option option = new Option(CommonConstants.PORT_OPTION, true,
        "Port that the server will be listening on.");
    Options options = new Options();
    options.addOption(option);
    CommandLineParser parser = new DefaultParser();
    CommandLine commands = parser.parse(options, args);
    Map<String, String> map = new HashMap<>();
    if (commands.hasOption(CommonConstants.PORT_OPTION)) {
      map.put(CommonConstants.PORT_OPTION, commands.getOptionValue(CommonConstants.PORT_OPTION));
    }
    return map;
  }
}
