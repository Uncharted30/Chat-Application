package client;

// A Java program for a Client
import client.bean.ConnectMsgRes;
import client.bean.LoginStatus;
import common.ConstantsUtil;
import common.MessageConstuctor;
import common.MessageProcessor;
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

    // connect firstly
    if (!connect()) {
      return;
    }

    // sendMessage thread
    SendMessage sendMessage = new SendMessage(username, stdIn, chatSocketOut, loginStatus);
    // readMessage thread
    ReadMessage readMessage = new ReadMessage(chatSocket, chatSocketIn, chatSocketOut, loginStatus);

    try {
      sendMessage.run();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      stdIn.close();
      chatSocketOut.close();
      countDownLatch.countDown();
    }

    try {
      readMessage.run();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      chatSocketIn.close();
      chatSocket.close();
      countDownLatch.countDown();
    }

    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private boolean connect() {
    // send connect msg
    byte[] connectMsg = MessageConstuctor.getTypeOneMsg(Arrays.asList(username), ConstantsUtil.CONNECT_MESSAGE);
    try {
      chatSocketOut.write(connectMsg);
      // read head
      chatSocketIn.readInt();
      // read space
      chatSocketIn.read();
      MessageProcessor messageProcessor = new MessageProcessor(chatSocketIn);
      ConnectMsgRes connectMsgRes = messageProcessor.processConnectResMsg();
      System.out.println(connectMsgRes.getContent());
      return connectMsgRes.isStatus();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
