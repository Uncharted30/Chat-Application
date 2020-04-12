package common.beans;

import common.ConvertUtil;
import common.beans.interfaces.ChatRoomProtocol;
import common.utils.ArrayUtil;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChatRoomProtocol implements ChatRoomProtocol {
  private void getBytesList(List<String> list, List<byte[]> result) {
    for (String str : list) {
      byte[] strBytes = str.getBytes();
      byte[] lengthBytes = ConvertUtil.intToByteArray(strBytes.length);
      result.add(lengthBytes);
      result.add(strBytes);
    }
  }

  protected byte[] toByteArray(int header, List<String> list) {
    List<byte[]> bytesList = new ArrayList<>();
    bytesList.add(ConvertUtil.intToByteArray(header));
    this.getBytesList(list, bytesList);
    return ArrayUtil.concat(bytesList);
  }

  protected byte[] toByteArray(int header, int num, List<String> list) {
    List<byte[]> bytesList = new ArrayList<>();
    bytesList.add(ConvertUtil.intToByteArray(header));
    bytesList.add(ConvertUtil.intToByteArray(num));
    this.getBytesList(list, bytesList);
    return ArrayUtil.concat(bytesList);
  }

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
