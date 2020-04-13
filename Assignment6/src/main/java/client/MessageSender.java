package client;

import client.bean.LoginStatus;
import common.beans.interfaces.ChatRoomProtocol;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * The type Send message.
 */
public class MessageSender implements Runnable {

  /**
   * read buffer from system.in
   */
  private BufferedReader stdIn = null;
  /**
   * output stream to chat socket
   */
  private DataOutputStream chatSocketOut = null;
  /**
   * sender username
   */
  private String username;
  /**
   * login status of the client
   */
  private volatile LoginStatus loginStatus;
  /**
   * count down latch
   */
  private CountDownLatch countDownLatch;

  /**
   * Instantiates a new Send message.
   *
   * @param username the username
   * @param stdIn the std in
   * @param chatSocketOut the chat socket out
   * @param loginStatus the login status
   * @param countDownLatch the count down latch
   */
  MessageSender(String username, BufferedReader stdIn, DataOutputStream chatSocketOut, LoginStatus loginStatus, CountDownLatch countDownLatch) {
    this.username = username;
    this.stdIn = stdIn;
    this.chatSocketOut = chatSocketOut;
    this.loginStatus = loginStatus;
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    UserInterface userInterface = new UserInterface(username, stdIn);
    try {
      while (loginStatus.isLogin()) {
        // read the message to deliver.
        // todo: parse input from UI
        ChatRoomProtocol msg = userInterface.getMessage();
        // write on the output stream
        if (msg != null) {
          chatSocketOut.write(msg.toByteArray());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      countDownLatch.countDown();
      try {
        stdIn.close();
        chatSocketOut.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
