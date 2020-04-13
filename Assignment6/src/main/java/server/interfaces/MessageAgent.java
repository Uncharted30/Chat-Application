package server.interfaces;

import common.beans.Message;
import java.io.IOException;

public interface MessageAgent {
  boolean sendBroadcastMessage(Message message) throws IOException;

  boolean sendDirectMessage(Message message) throws IOException;
}
