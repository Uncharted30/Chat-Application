package server;

import common.CommonConstants;
import common.beans.ConnectRes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import server.interfaces.Server;

public class ChatRoomServer extends Thread implements Server {

  private int port;
  private ServerSocket serverSocket;

  private volatile Boolean isRunning;
  private Map<String, ClientHandler> clients;
  private ExecutorService executor;
  private ServerMessageAgent serverMessageAgent;

  public ChatRoomServer(int port) throws IOException {
    if (port <= 0 || port > 0xFFFF) {
      this.port = CommonConstants.DEFAULT_PORT;
    } else {
      this.port = port;
    }
    this.serverSocket = new ServerSocket(this.port);
    this.port = this.serverSocket.getLocalPort();
    serverSocket.setSoTimeout(CommonConstants.ACCEPT_TIMEOUT);
    this.clients = new ConcurrentHashMap<>();
    this.executor = Executors.newFixedThreadPool(CommonConstants.MAX_CLIENTS);
    this.serverMessageAgent = new ServerMessageAgent(this.clients);
  }

  public ChatRoomServer() throws IOException {
    this(0);
  }

  @Override
  public void startServer() {
    this.isRunning = true;
    this.start();
    System.out.printf("The server is listening on port %d.\n", this.port);
  }

  @Override
  public void stopServer() {
    this.isRunning = false;
    this.executor.shutdown();
    for (ClientHandler clientHandler : clients.values()) {
      try {
        clientHandler.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    try {
      this.serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
      Socket socket = null;
      while (this.isRunning) {
        try {
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
                System.out.println(header);
                if (header == CommonConstants.CONNECT_MESSAGE) {
                  int len = dataInputStream.readInt();
                  dataInputStream.read();
                  byte[] usernameByte = new byte[len];
                  dataInputStream.read(usernameByte, 0, len);
                  String username = new String(usernameByte);
                  if (!this.clients.containsKey(username)) {
                    ClientHandler clientHandler = new ClientHandler(socket, this.serverMessageAgent);
                    this.executor.execute(clientHandler);
                    this.sendConnectionResponse(socket, true,
                        "There are " + this.clients.size() + " other connected clients.");
                    this.clients.put(username, clientHandler);
                  } else {
                    this.sendConnectionResponse(socket, false, "Username exits.");
                  }
                }
              } catch (SocketTimeoutException e) {
                this.sendConnectionResponse(socket, false, "Connection timeout!");
              }
            } else if (clients.size() > CommonConstants.MAX_CLIENTS) {
              this.sendConnectionResponse(socket, false,
                  "Connection rejected. Server has reached its capacity.");
            }
          }
        } catch (IOException e) {
//          e.printStackTrace();
          try {
            this.sendConnectionResponse(socket, false,
                "An IO exception occurred when establishing connection, please try again later.");
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        } finally {
          socket = null;
        }
      }
    } finally {
      this.stopServer();
    }
  }
}
