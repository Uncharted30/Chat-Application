package client;

import client.bean.ConnectMsgRes;
import client.bean.DisconnectMsgRes;
import client.bean.LoginStatus;
import common.ConstantsUtil;
import common.MessageProcessor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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

  public ReadMessage(Socket chatSocket, DataInputStream chatSocketIn, DataOutputStream dataOutputStream, LoginStatus loginStatus) {
    this.chatSocket = chatSocket;
    this.chatSocketIn = chatSocketIn;
    this.loginStatus = loginStatus;
  }

  @Override
  public void run() {
    MessageProcessor messageProcessor = new MessageProcessor(chatSocketIn);
    while (loginStatus.isLogin()) {
      try {
        int head = chatSocketIn.readInt();
        chatSocketIn.read();
        switch (head) {
          case ConstantsUtil.CONNECT_RESPONSE: disconnectHandler(messageProcessor.processConnectResMsg());
            break;
          case ConstantsUtil.QUERY_USER_RESPONSE:
            break;
          case ConstantsUtil.DIRECT_MESSAGE:
            break;
          default:
            System.out.println("Wrong message!");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void disconnectHandler(ConnectMsgRes disconnectMsgRes) {
    System.out.println(disconnectMsgRes.getContent());
    if (disconnectMsgRes.isStatus()) {
      loginStatus.setLogin(false);
    }
  }
}
