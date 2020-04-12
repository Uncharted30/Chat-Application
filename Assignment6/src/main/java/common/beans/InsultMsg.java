package common.beans;

import common.beans.interfaces.ChatRoomProtocol;

public class InsultMsg extends Message {

  private String sender;
  private String recipient;

  public InsultMsg(String sender, String recipient) {
    this.sender = sender;
    this.recipient = recipient;
  }

  @Override
  public String getSender() {
    return sender;
  }

  @Override
  public String getRecipient() {
    return recipient;
  }

  @Override
  public byte[] toByteArray() {
    return new byte[0];
  }

  @Override
  public void print() {

  }
}
