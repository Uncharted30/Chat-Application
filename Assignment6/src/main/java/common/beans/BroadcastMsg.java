package common.beans;

public class BroadcastMsg extends Message {

  private String sender;
  private String content;

  public BroadcastMsg(String sender, String content) {
    this.sender = sender;
    this.content = content;
  }

  @Override
  public String getSender() {
    return sender;
  }

  @Override
  public String getRecipient() {
    return null;
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
