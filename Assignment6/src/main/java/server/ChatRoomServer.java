package server;

import common.CommonConstants;
import common.beans.ConnectRes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import server.interfaces.Server;

/**
 * The type Chat room server, response to clients' connection request.
 */
public class ChatRoomServer extends Thread implements Server {

  private int port;
  private ServerSocket serverSocket;

  private volatile Boolean isRunning;
  private Map<String, ClientHandler> clients;
  private ExecutorService executor;
  private ServerMessageAgent serverMessageAgent;

  /**
   * Instantiates a new Chat room server listening on the specific port.
   *
   * @param port the port to listening on
   * @throws IOException the io exception
   */
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

  /**
   * Instantiates a new Chat room server which listening on a random available port.
   *
   * @throws IOException the io exception
   */
  public ChatRoomServer() throws IOException {
    this(0);
  }

  /**
   * Start server.
   */
  @Override
  public void startServer() {
    this.isRunning = true;
    this.start();
    System.out.printf("The server is listening on port %d.\n", this.port);
  }

  /**
   * Stop server, shut down all running threads and send clients disconnect message.
   */
  @Override
  public void stopServer() {
    this.executor.shutdown();
    for (ClientHandler clientHandler : clients.values()) {
      try {
        clientHandler.disconnect();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    this.isRunning = false;
    try {
      this.executor.awaitTermination(CommonConstants.TERMINATE_TIMEOUT, TimeUnit.MILLISECONDS);
      this.serverSocket.close();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sends clients connect response.
   *
   * @param socket the socket of client
   * @param status connection status
   * @param msg message to be sent to the client
   * @throws IOException the io exception
   */
  private void sendConnectionResponse(Socket socket, boolean status, String msg)
      throws IOException {
    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
    ConnectRes connectRes = new ConnectRes(status, msg);
    dataOutputStream.write(connectRes.toByteArray());
  }

  /**
   * Start accepting clients' connection request.
   */
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
                if (header == CommonConstants.CONNECT_MESSAGE) {
                  int len = dataInputStream.readInt();
                  dataInputStream.read();
                  byte[] usernameByte = new byte[len];
                  dataInputStream.read(usernameByte, 0, len);
                  String username = new String(usernameByte);
                  if (!this.clients.containsKey(username)) {
                    ClientHandler clientHandler = new ClientHandler(socket,
                        this.serverMessageAgent);
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
          if (socket != null) {
            try {
              this.sendConnectionResponse(socket, false,
                  "An IO exception occurred when establishing connection, please try again later.");
            } catch (IOException ex) {
              ex.printStackTrace();
            }
          }
        } finally {
          socket = null;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      this.stopServer();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatRoomServer that = (ChatRoomServer) o;
    return port == that.port &&
        serverSocket.equals(that.serverSocket) &&
        isRunning.equals(that.isRunning) &&
        clients.equals(that.clients) &&
        executor.equals(that.executor) &&
        serverMessageAgent.equals(that.serverMessageAgent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(port, serverSocket, isRunning, clients, executor, serverMessageAgent);
  }
}

