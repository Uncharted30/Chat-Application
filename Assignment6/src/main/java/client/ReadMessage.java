package client;

import client.bean.ConnectMsgRes;
import client.bean.DirectMsg;
import client.bean.LoginStatus;
import client.bean.QueryRes;
import common.ConstantsUtil;
import common.MessageProcessor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

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
          case ConstantsUtil.QUERY_USER_RESPONSE: queryResHandler(messageProcessor.processQueryMsg());
            break;
          case ConstantsUtil.DIRECT_MESSAGE: directMsgHandler(messageProcessor.processDirectMsg());
            break;
          case ConstantsUtil.FAILED_MESSAGE: failedMsgHandler(messageProcessor.processFailedMsg());
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
  private void failedMsgHandler(String failedMsg) {
    System.out.println(failedMsg);
  }

  private void directMsgHandler(DirectMsg directMsg) {
    System.out.println("Receive Message From " + directMsg.getSender() + ":");
    System.out.println(directMsg.getContent());
  }

  private void queryResHandler(QueryRes queryRes) {
    List<String> usernames = queryRes.getUsers();
    System.out.printf("There are total %d users in the chat room:\n", usernames.size());
    for (String username : usernames) {
      System.out.println(username);
    }
  }

}
