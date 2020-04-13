package client;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClientRunnerTest {
  @Test
  public void runWithWorngOption() {
    ClientRunner.main(new String[]{"-p", "8080"});
  }
  public void run() {
    ClientRunner.main(new String[]{"-p", "8080", "-u", "haolan"});
  }
}