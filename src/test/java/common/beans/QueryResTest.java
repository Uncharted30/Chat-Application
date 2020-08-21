package common.beans;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class QueryResTest {
  QueryRes queryRes;
  List<String> users = new ArrayList<>(Arrays.asList("haolan", "pan"));

  @Before
  public void setup() {
    queryRes = new QueryRes(users);
  }
  @Test
  public void getUserList() {
    assertEquals(users, queryRes.getUserList());
  }

  @Test
  public void getMessage() {
    StringBuilder message = new StringBuilder(
        "There are " + (users.size() + 1) + " in the chat room:");
    for (int i = 0; i < this.users.size(); i++) {
      message.append("\n").append(i + 1).append(". ").append(users.get(i));
    }
    assertEquals(message.toString(), queryRes.getMessage());
  }

  @Test
  public void testEquals() {
    QueryRes queryRes1 = new QueryRes(users);
    QueryRes queryRes2 = new QueryRes(new ArrayList<>());
    assertTrue(queryRes1.equals(queryRes));
    assertTrue(queryRes1.equals(queryRes1));
    assertFalse(queryRes1.equals(queryRes2));
    assertFalse(queryRes1.equals(null));
    assertFalse(queryRes1.equals("sf"));
  }

  @Test
  public void testHashCode() {
    QueryRes queryRes1 = new QueryRes(users);
    QueryRes queryRes2 = new QueryRes(new ArrayList<>());
    assertEquals(queryRes1.hashCode(), queryRes.hashCode());
    assertNotEquals(queryRes2.hashCode(), queryRes1.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("QueryRes{" +
        "userList=" + users +
        "} ", queryRes.toString());
  }
}