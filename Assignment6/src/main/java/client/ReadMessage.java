package client;

import client.bean.LoginStatus;
import common.CommonConstants;
import common.beans.ConnectRes;
import common.beans.interfaces.ChatRoomProtocol;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * The type Read message.
 */
public class ReadMessage implements Runnable {

  /**
   * input stream from chat socket
   */
  private DataInputStream chatSocketIn = null;
  /**
   * login status of the client
   */
  private volatile LoginStatus loginStatus;
  /**
   * chat socket
   */
  private Socket chatSocket;
  /**
   * count down latch
   */
  private CountDownLatch countDownLatch;

  /**
   * Instantiates a new Read message.
   *
   * @param chatSocketIn input stream from chat socket
   * @param chatSocket the chat socket
   * @param loginStatus the login status
   * @param countDownLatch the count down latch
   */
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
//        System.out.println(head);
        switch (head) {
          case CommonConstants.CONNECT_RESPONSE:
            ConnectRes connectRes = messageProcessor.processDisconnectResMsg();
            this.showMessage(connectRes);
            if (!connectRes.getStatus()) {
              loginStatus.setLogin(false);
              System.out.println("Press enter to quit!");
            }
            break;
          case CommonConstants.QUERY_USER_RESPONSE:
            this.showMessage(messageProcessor.processQueryMsg());
            break;
          case CommonConstants.DIRECT_MESSAGE:
            this.showMessage(messageProcessor.processDirectMsg());
            break;
          case CommonConstants.FAILED_MESSAGE:
            this.showMessage(messageProcessor.processFailedMsg());
            break;
          case CommonConstants.BROADCAST_MESSAGE:
            this.showMessage(messageProcessor.processBroadcastMsg());
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

  /**
   * show the msg
   * @param msg protocol msg
   */
  private void showMessage(ChatRoomProtocol msg) {
    System.out.println(msg.getMessage());
  }
}
