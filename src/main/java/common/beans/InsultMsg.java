package common.beans;

import common.CommonConstants;
import java.util.Arrays;
import java.util.Objects;

/**
 * The type Insult msg.
 */
public class InsultMsg extends AbstractChatRoomProtocol {

  private String sender;
  private String recipient;

  /**
   * Instantiates a new Insult msg.
   *
   * @param sender    the sender
   * @param recipient the recipient
   */
  public InsultMsg(String sender, String recipient) {
    this.sender = sender;
    this.recipient = recipient;
  }

  /**
   * Gets sender.
   *
   * @return the sender
   */
  public String getSender() {
    return sender;
  }

  /**
   * Gets recipient.
   *
   * @return the recipient
   */
  public String getRecipient() {
    return recipient;
  }

  /**
   * Serialize the object, the result could be transferred between server and client.
   *
   * @return the byte array
   */
  @Override
  public byte[] toByteArray() {
    return this
        .toByteArray(CommonConstants.SEND_INSULT, Arrays.asList(this.sender, this.recipient));
  }

  /**
   * Deserialize the byte array.
   *
   * @return the message
   */
  @Override
  public String getMessage() {
    return this.sender + " is trying to send an insult to " + this.recipient;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InsultMsg insultMsg = (InsultMsg) o;
    return sender.equals(insultMsg.sender) &&
        recipient.equals(insultMsg.recipient);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sender, recipient);
  }

  @Override
  public String toString() {
    return "InsultMsg{" +
        "sender='" + sender + '\'' +
        ", recipient='" + recipient + '\'' +
        "} " ;
  }
}
