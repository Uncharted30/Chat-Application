package client;

// A Java program for a Client
import client.bean.LoginStatus;
import common.beans.ConnectMsg;
import common.beans.ConnectRes;
import java.net.*;
import java.io.*;
import java.util.concurrent.CountDownLatch;

public class Client {

  /**
   * chat socket
   */
  private Socket chatSocket = null;
  /**
   * chat socket output stream
   */
  private DataOutputStream chatSocketOut = null;
  /**
   * chat socket input stream
   */
  private DataInputStream chatSocketIn = null;
  /**
   * standard in
   */
  private BufferedReader stdIn = null;
  /**
   * username of the client
   */
  private String username = "";
  private volatile LoginStatus loginStatus = null;

  private CountDownLatch countDownLatch = new CountDownLatch(2);

  /**
   *
   * @param address the ip address of the server
   * @param port the port of the server
   * @param username the username of the client
   */
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

  /**
   * run the client
   * @throws IOException when there are some IOException in send message and read message thread
   */
  public void run() throws IOException {

    // connect first
    if (!connect()) {
      return;
    }

    // sendMessage thread
    MessageSender messageSender = new MessageSender(username, stdIn, chatSocketOut, loginStatus, countDownLatch);
    // readMessage thread
    MessageReader messageReader = new MessageReader(chatSocketIn, chatSocket, loginStatus, countDownLatch);

    new Thread(messageSender).start();

    new Thread(messageReader).start();

    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * check if the client have connected to the server
   * @return the connect status
   */
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
