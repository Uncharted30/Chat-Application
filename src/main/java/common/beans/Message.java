package common.beans;

import java.util.Arrays;
import java.util.Objects;

/**
 * The type Message, which contains a sender, a recipient and content.
 */
public abstract class Message extends AbstractChatRoomProtocol {

  /**
   * The Sender.
   */
  protected String sender;
  /**
   * The Recipient.
   */
  protected String recipient;
  /**
   * The Content.
   */
  protected byte[] content;

  /**
   * Instantiates a new Message.
   *
   * @param sender    the sender
   * @param recipient the recipient
   * @param content   the content
   */
  public Message(String sender, String recipient, byte[] content) {
    this.sender = sender;
    this.recipient = recipient;
    this.content = content;
  }

  /**
   * Gets sender.
   *
   * @return the sender
   */
  public String getSender() {
    return this.sender;
  }

  /**
   * Gets recipient.
   *
   * @return the recipient
   */
  public String getRecipient() {
    return this.recipient;
  }

  /**
   * Get content byte [ ].
   *
   * @return the byte [ ]
   */
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
        "} ";
  }
}
