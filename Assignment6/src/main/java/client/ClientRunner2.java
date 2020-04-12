package client;

import client.util.CommandParser;
import common.CommonConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.commons.cli.ParseException;

public class ClientRunner2 {

  public static void main(String[] args) {
    final BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    try {
      stdIn.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          stdIn.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
    thread2.start();
  }
}
