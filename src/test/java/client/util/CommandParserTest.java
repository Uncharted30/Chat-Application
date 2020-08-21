package client.util;

import static org.junit.Assert.*;

import common.CommonConstants;
import java.util.Map;
import org.apache.commons.cli.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CommandParserTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  @Test
  public void parseWithFullOption() throws Exception {
    String ip = "127.0.0.1";
    String username = "haolan";
    String port = "4008";
    Map<String, String> parseRes = CommandParser.parse(new String[]{"-" + CommonConstants.IP_OPTION, ip, "-" + CommonConstants.USERNAME_OPTION, username, "-" + CommonConstants.PORT_OPTION, port});
    assertEquals(ip, parseRes.get(CommonConstants.IP_OPTION));
    assertEquals(username, parseRes.get(CommonConstants.USERNAME_OPTION));
    assertEquals(port, parseRes.get(CommonConstants.PORT_OPTION));
  }

  @Test
  public void parseWithWrongOption1() throws Exception {
    thrown.expect(ParseException.class);
    Map<String, String> parseRes = CommandParser.parse(new String[]{"-" + CommonConstants.IP_OPTION});
  }

  @Test
  public void parseWithWrongOption2() throws Exception {
    thrown.expect(ParseException.class);
    Map<String, String> parseRes = CommandParser.parse(new String[]{"-" + CommonConstants.PORT_OPTION, "23"});
  }

  @Test
  public void parseWithWrongOption3() throws Exception {
    thrown.expect(ParseException.class);
    Map<String, String> parseRes = CommandParser.parse(new String[]{"-" + CommonConstants.USERNAME_OPTION, "sdfds"});
  }
}