package client;

import client.bean.LoginStatus;
import common.CommonConstants;
import common.beans.BroadcastMsg;
import common.beans.ConnectRes;
import common.beans.DirectMsg;
import common.beans.QueryRes;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReadMessage implements Runnable {

  /**
   * input stream from chat socket
   */
  private DataInputStream chatSocketIn = null;
  /**
   * login status of the client
   */
  private volatile LoginStatus loginStatus;

  private Socket chatSocket;

  private CountDownLatch countDownLatch;

  public ReadMessage(DataInputStream chatSocketIn, Socket chatSocket, LoginStatus loginStatus,
      CountDownLatch countDownLatch) {
    this.chatSocketIn = chatSocketIn;
    this.loginStatus = loginStatus;
    this.chatSocket = chatSocket;
    this.countDownLatch = countDownLatch;
  }

  @Override
  public void run() {
    System.out.println("Client start running...");
    ClientMessageProcessor messageProcessor = new ClientMessageProcessor(chatSocketIn);
    try {
      while (loginStatus.isLogin()) {
        int head = chatSocketIn.readInt();
        chatSocketIn.read();
        System.out.println(head);
        switch (head) {
          case CommonConstants.CONNECT_RESPONSE:
            disconnectHandler(messageProcessor.processDisconnectResMsg());
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
          case CommonConstants.BROADCAST_MESSAGE:
            broadcastMsgHandler(messageProcessor.processBroadcastMsg());
            break;
          default:
            System.out.println("Wrong message!");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      countDownLatch.countDown();
      try {
        chatSocketIn.close();
        chatSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void disconnectHandler(ConnectRes disconnectMsgRes) {
    System.out.println(disconnectMsgRes.getContent());
    if (!disconnectMsgRes.getStatus()) {
      loginStatus.setLogin(false);
    }
  }

  private void failedMsgHandler(String failedMsg) {
    System.out.println(failedMsg);
  }

  private void directMsgHandler(DirectMsg directMsg) {
    System.out
        .println(directMsg.getSender() + " sent you a direct message: " + directMsg.getMessage());
  }

  private void broadcastMsgHandler(BroadcastMsg broadcastMsg) {
    System.out.println(broadcastMsg.getSender() + ": " + broadcastMsg.getMessage());
  }

  private void queryResHandler(QueryRes queryRes) {
    List<String> userList = queryRes.getUserList();
    System.out.printf("There are total %d users in the chat room:\n", userList.size() + 1);
    for (String username : userList) {
      System.out.println(username);
    }
  }

}
