package client.bean;

public class DisconnectMsgRes {
  private boolean status;
  private String content;

  public DisconnectMsgRes(boolean status, String content) {
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
