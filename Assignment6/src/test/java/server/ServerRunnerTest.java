package server;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.Test;

public class ServerRunnerTest {

  @Test
  public void main() throws IOException, InterruptedException {
    String data = "exit";
    InputStream stdin = System.in;
    try {
      System.setIn(new ByteArrayInputStream(data.getBytes()));
      ServerRunner.main(new String[]{});
    } finally {
      System.setIn(stdin);
    }
  }
}