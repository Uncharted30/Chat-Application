package server;
// A Java program for a Server

import common.MessageProcessor;
import java.net.*;
import java.io.*;

public class Server {

  //initialize socket and input stream
  private Socket socket;
  private ServerSocket server;
  private DataInputStream in;

  // constructor with port
  public Server(int port) {
    // starts server and waits for a connection
    try {
      server = new ServerSocket(0);
      System.out.println("Server started at Port" + server.getLocalPort());

      System.out.println("Waiting for a client ...");

      socket = server.accept();
      System.out.println("Client accepted");

      // takes input from the client socket
      in = new DataInputStream(socket.getInputStream());

      String line = "";

      // reads message from client until "Over" is sent
      while (true) {
        try {
          System.out.println(in.readInt());
          in.read();
          MessageProcessor messageProcessor = new MessageProcessor(in);
          System.out.println(messageProcessor.processLogOffMsg());
        } catch (IOException i) {
          System.out.println(i);
        }
      }
    } catch (IOException i) {
      System.out.println(i);
    }
  }

  public static void main(String args[]) {
    Server server = new Server(5000);
  }
}

