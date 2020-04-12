package server;

import common.CommonConstants;
import common.beans.ConnectRes;
import common.beans.FailedMsg;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {

  private int port;
  private ServerSocket serverSocket;
  private volatile Boolean isRunning;
  private Map<String, ClientHandler> clients;
  private ExecutorService executor;
  private MessageAgent messageAgent;

  public Server(int port) throws IOException {
    if (port <= 0 || port > 0xFFFF) {
      this.port = CommonConstants.DEFAULT_PORT;
    } else {
      this.port = port;
    }
    this.serverSocket = new ServerSocket(this.port);
    this.port = this.serverSocket.getLocalPort();
    serverSocket.setSoTimeout(CommonConstants.ACCEPT_TIMEOUT);
    this.clients = new HashMap<>();
    this.executor = Executors.newFixedThreadPool(CommonConstants.MAX_CLIENTS);
    this.messageAgent = new MessageAgent(this.clients);
  }

  public Server() throws IOException {
    this(0);
  }

  public synchronized void startServer() {
    this.isRunning = true;
    this.start();
    System.out.printf("The server is listening on port %d.\n", this.port);
  }

  public synchronized void stopServer() {
    this.isRunning = false;
  }

  private void sendConnectionResponse(Socket socket, boolean status, String msg)
      throws IOException {
    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
    ConnectRes connectRes = new ConnectRes(status, msg);
    dataOutputStream.write(connectRes.toByteArray());
    System.out.println(Arrays.toString(connectRes.toByteArray()));
  }

  @Override
  public void run() {
    try {
      while (this.isRunning) {
        Socket socket = null;
        try {
          socket = this.serverSocket.accept();
        } catch (SocketTimeoutException e) {
//          e.printStackTrace();
        }

        if (socket != null) {
          if (clients.size() < CommonConstants.MAX_CLIENTS) {
            socket.setSoTimeout(CommonConstants.CONNECTION_TIMEOUT);
            try {
              DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
              int header = dataInputStream.readInt();
              dataInputStream.read();
              if (header == CommonConstants.CONNECT_MESSAGE) {
                int len = dataInputStream.readInt();
                dataInputStream.read();
                byte[] usernameByte = new byte[len];
                dataInputStream.read(usernameByte, 0, len);
                String username = new String(usernameByte);
                ClientHandler clientHandler = new ClientHandler(socket, this.messageAgent);
                this.executor.execute(clientHandler);
                this.sendConnectionResponse(socket, true,
                    "There are " + this.clients.size() + " other connected clients.");
                this.clients.put(username, clientHandler);
              }
            } catch (SocketTimeoutException e) {
              this.sendConnectionResponse(socket, false, "Connection timeout!");
            }
          } else if (clients.size() > CommonConstants.MAX_CLIENTS) {
            this.sendConnectionResponse(socket, true,
                "Connection rejected. Server has reached its capacity.");
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

