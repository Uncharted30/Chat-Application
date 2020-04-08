package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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

  SendMessage(String username, BufferedReader stdIn, DataOutputStream chatSocketOut, LoginStatus loginStatus) {
    this.username = username;
    this.stdIn = stdIn;
    this.chatSocketOut = chatSocketOut;
    this.loginStatus = loginStatus;
  }

  @Override
  public void run() {
    while (loginStatus.isLogin()) {
      // read the message to deliver.
      try {
        // todo: parse input from UI
        String msg = stdIn.readLine();
        // write on the output stream
        chatSocketOut.write(msg.getBytes());
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
