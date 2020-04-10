package client.bean;

public class ConnectMsgRes {

  private boolean status;
  private String content;

  public ConnectMsgRes(boolean status, String content) {
    this.status = status;
    this.content = content;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
