package common.beans;

public class DirectMsg extends Message {
  private String sender;
  private String recipient;
  private String content;

  public DirectMsg(String sender, String recipient, String content) {
    this.sender = sender;
    this.recipient = recipient;
    this.content = content;
  }

  @Override
  public String getSender() {
    return sender;
  }

  @Override
  public String getRecipient() {
    return recipient;
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
