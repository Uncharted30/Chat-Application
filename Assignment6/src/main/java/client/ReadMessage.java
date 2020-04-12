package client;

import client.bean.LoginStatus;
import common.CommonConstants;
import common.beans.ConnectRes;
import common.beans.DirectMsg;
import common.beans.QueryRes;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

public class ReadMessage implements Runnable {

  /**
   * input stream from chat socket
   */
  private DataInputStream chatSocketIn = null;
  /**
   * login status of the client
   */
  private volatile LoginStatus loginStatus;

  public ReadMessage(DataInputStream chatSocketIn, LoginStatus loginStatus) {
    this.chatSocketIn = chatSocketIn;
    this.loginStatus = loginStatus;
  }

  @Override
  public void run() {
    System.out.println("Client start running...");
    ClientMessageProcessor messageProcessor = new ClientMessageProcessor(chatSocketIn);
    while (loginStatus.isLogin()) {
      try {
        int head = chatSocketIn.readInt();
        chatSocketIn.read();
        System.out.println(head);
        switch (head) {
          case CommonConstants.CONNECT_RESPONSE:
            disconnectHandler(messageProcessor.processConnectResMsg());
            break;
          case CommonConstants.QUERY_USER_RESPONSE:
            queryResHandler(messageProcessor.processQueryMsg());
            break;
          case CommonConstants.DIRECT_MESSAGE:
            directMsgHandler(messageProcessor.processDirectMsg());
            break;
          case CommonConstants.FAILED_MESSAGE:
            failedMsgHandler(messageProcessor.processFailedMsg());
            break;
          default:
            System.out.println("Wrong message!");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void disconnectHandler(ConnectRes disconnectMsgRes) {
    System.out.println(disconnectMsgRes.getContent());
    if (disconnectMsgRes.getStatus()) {
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
    List<String> userList = queryRes.getUserList();
    System.out.printf("There are total %d users in the chat room:\n", userList.size());
    for (String username : userList) {
      System.out.println(username);
    }
  }

}
