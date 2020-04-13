package client;

// A Java program for a Client
import client.bean.LoginStatus;
import common.CommonConstants;
import common.beans.ConnectMsg;
import common.beans.ConnectRes;
import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Client {

  private Socket chatSocket = null;

  private DataOutputStream chatSocketOut = null;
  private DataInputStream chatSocketIn = null;

  private BufferedReader stdIn = null;
  private String username = "";
  private volatile LoginStatus loginStatus = null;

  private CountDownLatch countDownLatch = new CountDownLatch(2);

  public Client(String address, int port, String username) {
    this.username = username;
    try {
      chatSocket = new Socket(address, port);
      chatSocketOut = new DataOutputStream(chatSocket.getOutputStream());
      chatSocketIn = new DataInputStream(chatSocket.getInputStream());
      stdIn = new BufferedReader(new InputStreamReader(System.in));
      loginStatus = new LoginStatus(true);
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: " + address);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for "
          + "the connection to: " + address + ".");
      System.exit(1);
    }
  }

  public void run() throws IOException {

    // connect first
    if (!connect()) {
      return;
    }

    // sendMessage thread
    SendMessage sendMessage = new SendMessage(username, stdIn, chatSocketOut, loginStatus, countDownLatch);
    // readMessage thread
    ReadMessage readMessage = new ReadMessage(chatSocketIn, chatSocket, loginStatus, countDownLatch);

    new Thread(sendMessage).start();

    new Thread(readMessage).start();

    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private boolean connect() {
    // send connect msg
    ConnectMsg connectMsg = new ConnectMsg(username);
    try {
      chatSocketOut.write(connectMsg.toByteArray());
      // read head
      chatSocketIn.readInt();
      // read space
      chatSocketIn.read();
      ClientMessageProcessor messageProcessor = new ClientMessageProcessor(chatSocketIn);
      ConnectRes connectRes = messageProcessor.processConnectResMsg();
      System.out.println(connectRes.getContent());
      return connectRes.getStatus();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

}
