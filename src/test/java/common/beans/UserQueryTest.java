package common.beans;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserQueryTest {
  UserQuery userQuery;
  String username = "haolan";

  @Before
  public void setup() {
    userQuery = new UserQuery(username);
  }
  @Test
  public void getUsername() {
    assertEquals(username, userQuery.getUsername());
  }

  @Test
  public void getMessage() {
    assertEquals( "User " + this.username + " is requesting user list.", userQuery.getMessage());
  }

  @Test
  public void testEquals() {
    UserQuery userQuery1 = new UserQuery(username);
    UserQuery userQuery2 = new UserQuery("sfs");
    assertTrue(userQuery1.equals(userQuery));
    assertTrue(userQuery1.equals(userQuery1));
    assertFalse(userQuery1.equals(userQuery2));
    assertFalse(userQuery1.equals(null));
    assertFalse(userQuery1.equals("sf"));
  }

  @Test
  public void testHashCode() {
    UserQuery userQuery1 = new UserQuery(username);
    UserQuery userQuery2 = new UserQuery("sfs");
    assertEquals(userQuery1.hashCode(), userQuery.hashCode());
    assertNotEquals(userQuery2.hashCode(), userQuery1.hashCode());
  }


  @Test
  public void testToString() {
    assertEquals("UserQuery{" +
        "username='" + username + '\'' +
        "} " , userQuery.toString());
  }
}