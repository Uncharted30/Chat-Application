package common;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConstantsUtil {

  public static final int CONNECT_MESSAGE = 19;
  public static final int CONNECT_RESPONSE = 20;
  public static final int DISCONNECT_MESSAGE = 21;
  public static final int QUERY_CONNECTED_USERS = 22;
  public static final int QUERY_USER_RESPONSE = 23;
  public static final int BROADCAST_MESSAGE = 24;
  public static final int DIRECT_MESSAGE = 25;
  public static final int FAILED_MESSAGE = 26;
  public static final int SEND_INSULT = 27;

  public static final String IP_OPTION = "i";
  public static final String PORT_OPTION = "p";
  public static final String USERNAME_OPTION = "u";

  public static final String DEFAULT_SERVER_IP = "127.0.0.1";

  public static final String HELP_CMD = "?";
  public static final String LOG_OFF_CMD = "logoff";
  public static final String SHOW_USERS_CMD = "who";
  public static final String SEND_MESSAGE_CMD = "@user";
  public static final String BROADCAST_CMD = "@all";
  public static final String SEND_INSULT_CMD = "!user";

  public static final Set<String> CMD_SET = new HashSet<>(Arrays
      .asList(HELP_CMD, LOG_OFF_CMD, SHOW_USERS_CMD, SEND_MESSAGE_CMD, BROADCAST_CMD,
          SEND_INSULT_CMD));

  public static final int MSG_HEAD_LEN = 4;
  public static final int MSG_SPLIT_LEN = 1;
  public static final int MSG_SIZE_LEN = 4;

  public static byte SPACE_BYTE = " ".getBytes()[0];

  public static final int MAX_CLIENTS = 10;
}
