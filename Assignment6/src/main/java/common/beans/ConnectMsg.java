package common.beans.requests;

public class ConnectMsg extends Request {

  String username;

  public ConnectMsg(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public byte[] toByteArray() {
    return new byte[0];
  }

  @Override
  public void log() {
    System.out.println("User " + this.username + " is trying to connect to the server.");
  }
}
