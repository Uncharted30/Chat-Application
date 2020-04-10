package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadMessage implements Runnable {

  /**
   * input stream from chat socket
   */
  private DataInputStream chatSocketIn = null;
  /**
   * chat socket
   */
  private Socket chatSocket = null;
  /**
   * login status of the client
   */
  private volatile LoginStatus loginStatus;

  public ReadMessage(Socket chatSocket, DataInputStream chatSocketIn, LoginStatus loginStatus) {
    this.chatSocket = chatSocket;
    this.chatSocketIn = chatSocketIn;
    this.loginStatus = loginStatus;
  }

  @Override
  public void run() {
    while (loginStatus.isLogin()) {
      try {
        int messageLen= chatSocketIn.readInt();

        if (messageLen > 0) {
          byte[] message = new byte[messageLen];
          chatSocketIn.readFully(message, 0, message.length);
          //todo: pares input from chat socket
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
