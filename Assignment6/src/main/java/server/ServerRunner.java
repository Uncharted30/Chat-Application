package server;

import java.io.IOException;

public class ServerRunner {

  public static void main(String[] args) throws IOException, InterruptedException {
    Server server = new Server();
    server.startServer();
  }
}
