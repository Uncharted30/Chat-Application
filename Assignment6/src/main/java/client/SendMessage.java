package client;

import client.bean.LoginStatus;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

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
    UserInterface userInterface = new UserInterface(username, stdIn);
    while (loginStatus.isLogin()) {
      // read the message to deliver.
      try {
        // todo: parse input from UI
        byte[] msg = userInterface.getMessage();
        // write on the output stream
        if (msg.length > 0) {
          System.out.printf("write %d length bytes\n", msg.length);
          chatSocketOut.write(msg);
        }
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
