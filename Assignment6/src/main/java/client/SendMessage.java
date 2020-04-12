package client;

import client.bean.LoginStatus;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class SendMessage implements Runnable {

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

  private CountDownLatch countDownLatch;

  SendMessage(String username, BufferedReader stdIn, DataOutputStream chatSocketOut, LoginStatus loginStatus, CountDownLatch countDownLatch) {
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
        byte[] msg = userInterface.getMessage();
        // write on the output stream
        if (msg.length > 0) {
          System.out.printf("write %d length bytes\n", msg.length);
          chatSocketOut.write(msg);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      countDownLatch.countDown();
      try {
        chatSocketOut.close();
        stdIn.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
