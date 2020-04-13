package client.bean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LoginStatusTest {
  LoginStatus loginStatus = null;
  @Before
  public void setup() {
    boolean isLogin = false;
    loginStatus = new LoginStatus(isLogin);
  }
  @Test
  public void isLogin() {
    assertFalse(loginStatus.isLogin());
  }

  @Test
  public void setLogin() {
    loginStatus.setLogin(true);
    assertTrue(loginStatus.isLogin());
  }

}