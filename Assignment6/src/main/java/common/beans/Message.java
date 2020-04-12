package common.beans;

import common.beans.interfaces.ChatRoomProtocol;

public abstract class Message extends AbstractChatRoomProtocol {

  public abstract String getSender();

  public abstract String getRecipient();
}
