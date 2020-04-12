package common.beans.response;

public class ConnectRes extends Response {

  private boolean status;
  private String content;

  public ConnectRes(boolean status, String content) {
    this.status = status;
    this.content = content;
  }

  public boolean getStatus() {
    return status;
  }

  public String getContent() {
    return content;
  }

  @Override
  public byte[] toByteArray() {
    return new byte[0];
  }

  @Override
  public void print() {

  }
}
