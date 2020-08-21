package client.bean;

/**
 * The type Login status.
 */
public class LoginStatus {
  private volatile boolean isLogin;

  /**
   * Instantiates a new Login status.
   *
   * @param isLogin the status of the client
   */
  public LoginStatus(boolean isLogin) {
    this.isLogin = isLogin;
  }

  /**
   * get the client status
   *
   * @return client status
   */
  public boolean isLogin() {
    return isLogin;
  }

  /**
   * set the client status.
   *
   * @param login client new status
   */
  public void setLogin(boolean login) {
    isLogin = login;
  }
}
