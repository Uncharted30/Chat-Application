package common.beans.interfaces;

/**
 * The interface Chat room protocol.
 */
public interface ChatRoomProtocol {

  /**
   * Serialize the object, the result could be transferred between server and client.
   *
   * @return the byte array
   */
  byte[] toByteArray();

  /**
   * Deserialize the byte array.
   *
   * @return the message
   */
  String getMessage();
}
