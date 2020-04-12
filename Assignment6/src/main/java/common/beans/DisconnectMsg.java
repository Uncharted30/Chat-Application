package common.beans.requests;

public class DisconnectMsg extends Request {

  private String username;

  public DisconnectMsg(String username) {
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
    System.out.println("User " + this.username + "is trying to disconnect.");
  }
}
