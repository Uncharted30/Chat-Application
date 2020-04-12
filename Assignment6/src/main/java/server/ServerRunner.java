package server;

import java.io.IOException;

public class Runner {

  public static void main(String[] args) throws IOException, InterruptedException {
    Server server = new Server();
    server.startServer();
  }
}
