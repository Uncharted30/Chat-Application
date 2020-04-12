package common.utils;

import common.CommonConstants;
import java.util.Arrays;
import java.util.List;

public class ArrayUtil {
//  public static <T> T[] concat(T[] first, T[]... rest) {
//    int totalLength = first.length;
//    for (T[] arr : rest) {
//      totalLength += arr.length;
//    }
//    T[] result = arrs.copyOf(first, totalLength);
//    int offset = first.length;
//    for (T[] arr : rest) {
//      System.arrcopy(arr, 0, result, offset, arr.length);
//      offset += arr.length;
//    }
//    return result;
//  }

  public static byte[] concat(List<byte[]> list) {
    if (list.isEmpty()) return new byte[0];
    if (list.size() == 1) return list.get(0);

    byte[] first = list.get(0);

    int len = first.length;
    for (int i = 1; i < list.size(); i++) {
      len += list.get(i).length + 1;
    }

    byte[] result = Arrays.copyOf(first, len);
    int offset = first.length;
    for (int i = 1; i < list.size(); i++) {
      byte[] arr = list.get(i);
      result[offset++] = CommonConstants.SPACE_BYTE;
      System.arraycopy(arr, 0, result, offset, arr.length);
      offset += arr.length;
    }
    return result;
  }
}
