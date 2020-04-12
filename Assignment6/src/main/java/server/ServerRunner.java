package server;

import java.io.IOException;
import java.util.Scanner;

public class ServerRunner {

  public static void main(String[] args) throws IOException, InterruptedException {
    Server server = new Server();
    server.startServer();
    String command = "";
    Scanner in = new Scanner(System.in);
    while (!"exit".equals(command)) {
      command = in.nextLine();
    }
    System.out.println(command);
    server.stopServer();
  }
}
