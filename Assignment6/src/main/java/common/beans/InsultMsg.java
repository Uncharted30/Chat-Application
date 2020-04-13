package common.beans;

import common.CommonConstants;
import common.beans.interfaces.ChatRoomProtocol;
import java.util.Arrays;
import java.util.Objects;

public class InsultMsg extends AbstractChatRoomProtocol {

  private String sender;
  private String recipient;

  public InsultMsg(String sender, String recipient) {
    this.sender = sender;
    this.recipient = recipient;
  }

  public String getSender() {
    return sender;
  }

  public String getRecipient() {
    return recipient;
  }

  @Override
  public byte[] toByteArray() {
    return this.toByteArray(CommonConstants.SEND_INSULT, Arrays.asList(this.sender, this.recipient));
  }

  @Override
  public String getMessage() {
    return null;
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
        "} " + super.toString();
  }
}
