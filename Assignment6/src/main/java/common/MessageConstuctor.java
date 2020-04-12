package common;


import java.util.ArrayList;
import java.util.List;

public class MessageConstuctor {

  private static byte[] getHeadMsg(int head) {
    return ConvertUtil.intToByteArray(head);
  }

  private static byte[] getStringMsg(String content) {
    int len = CommonConstants.MSG_SIZE_LEN + CommonConstants.MSG_SPLIT_LEN + content.length();
    byte[] msgBytes = new byte[len];
    // length of the content
    byte[] contentLenBytes = ConvertUtil.intToByteArray(content.length());
    // content
    byte[] contentBytes = content.getBytes();
    int cur = 0;
    // add length
    for (int i = 0; i < CommonConstants.MSG_SIZE_LEN; i++) {
      msgBytes[cur++] = contentLenBytes[i];
    }
    // add space
    msgBytes[cur++] = CommonConstants.SPACE_BYTE;
    // add content
    for (int i = 0; i < contentBytes.length; i++) {
      msgBytes[cur++] = contentBytes[i];
    }
    return msgBytes;
  }

  public static byte[] getTypeOneMsg(List<String> contents, int headID) {
    // construct the content
    List<Byte> tempContentBytes = new ArrayList<>();
    for (String content : contents) {
      byte[] contentMsg = getStringMsg(content);
      for (byte item : contentMsg) {
        tempContentBytes.add(item);
      }
      tempContentBytes.add(CommonConstants.SPACE_BYTE);
    }
    tempContentBytes.remove(tempContentBytes.size() - 1);
    byte[] contentBytes = new byte[tempContentBytes.size()];
    for (int i = 0; i < tempContentBytes.size(); i++) {
      contentBytes[i] = tempContentBytes.get(i);
    }
    // construct the head
    byte[] head = getHeadMsg(headID);

    // construct the msg
    int len = head.length + CommonConstants.MSG_SPLIT_LEN + contentBytes.length;
    byte[] msgBytes = new byte[len];
    int cur = 0;
    // add head
    for (int i = 0; i < head.length; i++) {
      msgBytes[cur++] = head[i];
    }
    // add space
    msgBytes[cur++] = CommonConstants.SPACE_BYTE;
    // add content
    for (int i = 0; i < contentBytes.length; i++) {
      msgBytes[cur++] = contentBytes[i];
    }
    return msgBytes;
  }
}
