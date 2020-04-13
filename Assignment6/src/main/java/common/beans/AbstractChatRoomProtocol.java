package common.beans;

import common.utils.ConvertUtil;
import common.beans.interfaces.ChatRoomProtocol;
import common.utils.ArrayUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Abstract chat room protocol.
 */
public abstract class AbstractChatRoomProtocol implements ChatRoomProtocol {

  /**
   * Serialize a String list to byte array.
   *
   * @param list the String list to be serialized.
   * @param result the byte array
   */
  private void getBytesList(List<String> list, List<byte[]> result) {
    for (String str : list) {
      byte[] strBytes = str.getBytes();
      byte[] lengthBytes = ConvertUtil.intToByteArray(strBytes.length);
      result.add(lengthBytes);
      result.add(strBytes);
    }
  }

  /**
   * Serialize a request or response.
   *
   * @param header the header
   * @param list   the String list to be serialized.
   * @return the byte [ ]
   */
  protected byte[] toByteArray(int header, List<String> list) {
    List<byte[]> bytesList = new ArrayList<>();
    bytesList.add(ConvertUtil.intToByteArray(header));
    this.getBytesList(list, bytesList);
    return ArrayUtil.concat(bytesList);
  }

  /**
   * Serialize a request or response.
   *
   * @param header the header
   * @param num    the number to be serialized
   * @param list   the String list to be serialized.
   * @return the byte array
   */
  protected byte[] toByteArray(int header, int num, List<String> list) {
    List<byte[]> bytesList = new ArrayList<>();
    bytesList.add(ConvertUtil.intToByteArray(header));
    bytesList.add(ConvertUtil.intToByteArray(num));
    this.getBytesList(list, bytesList);
    return ArrayUtil.concat(bytesList);
  }

  /**
   * Serialize a request or response.
   *
   * @param header the header
   * @param msg    the message to be serialized.
   * @return the byte array
   */
  protected byte[] toByteArray(int header, String msg) {
    List<byte[]> bytesList = new ArrayList<>();
    bytesList.add(ConvertUtil.intToByteArray(header));
    byte[] strBytes = msg.getBytes();
    byte[] lengthBytes = ConvertUtil.intToByteArray(strBytes.length);
    bytesList.add(lengthBytes);
    bytesList.add(strBytes);
    return ArrayUtil.concat(bytesList);
  }
}
