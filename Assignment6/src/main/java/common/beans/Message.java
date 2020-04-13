package common.beans;

import common.beans.interfaces.ChatRoomProtocol;
import java.util.Arrays;
import java.util.Objects;

public abstract class Message extends AbstractChatRoomProtocol {

  protected String sender;
  protected String recipient;
  protected byte[] content;

  public Message(String sender, String recipient, byte[] content) {
    this.sender = sender;
    this.recipient = recipient;
    this.content = content;
  }

  public String getSender() {
    return this.sender;
  }

  public String getRecipient() {
    return this.recipient;
  }

  public byte[] getContent() {
    return content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Message message = (Message) o;
    return sender.equals(message.sender) &&
        recipient.equals(message.recipient) &&
        Arrays.equals(content, message.content);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(sender, recipient);
    result = 31 * result + Arrays.hashCode(content);
    return result;
  }

  @Override
  public String toString() {
    return "Message{" +
        "sender='" + sender + '\'' +
        ", recipient='" + recipient + '\'' +
        ", content=" + Arrays.toString(content) +
        "} " + super.toString();
  }
}
