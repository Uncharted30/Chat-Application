package server;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Before;
import org.junit.Test;

public class ServerMessageAgentTest {

  private ServerMessageAgent serverMessageAgent1;
  private ServerMessageAgent serverMessageAgent2;

  @Before
  public void setUp() throws Exception {
    this.serverMessageAgent1 = new ServerMessageAgent(new HashMap<>());
    this.serverMessageAgent2 = new ServerMessageAgent(new ConcurrentHashMap<>());
  }

  @Test
  public void testEquals() {
    assertNotEquals(this.serverMessageAgent1, null);
    assertNotEquals(this.serverMessageAgent1, new ArrayList<>());
    assertNotEquals(this.serverMessageAgent1, this.serverMessageAgent2);
    assertEquals(this.serverMessageAgent1, this.serverMessageAgent1);
  }

  @Test
  public void testHashCode() {
    assertNotEquals(this.serverMessageAgent1.hashCode(), this.serverMessageAgent2.hashCode());
  }
}