package server.interfaces;

import common.beans.Message;
import java.io.IOException;

/**
 * The type Message agent, client handler threads send message to other clients through
 * message agent.
 */
public interface MessageAgent {

  /**
   * Send broadcast message to every client that is connected to the server.
   *
   * @param message the message to be sent
   * @return true if the agent sent message successfully
   * @throws IOException the io exception
   */
  boolean sendBroadcastMessage(Message message) throws IOException;

  /**
   * Send direct message to one specific client.
   *
   * @param message the message to be sent
   * @return true if the agent sent message successfully
   * @throws IOException the io exception
   */
  boolean sendDirectMessage(Message message) throws IOException;
}
