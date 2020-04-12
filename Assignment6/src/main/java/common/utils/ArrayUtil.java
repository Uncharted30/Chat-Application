package server.utils;

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

    int len = 0;
    for (byte[] arr : list) {
      len += arr.length;
    }

    byte[] first = list.get(0);
    byte[] result = Arrays.copyOf(first, len);
    int offset = first.length;
    for (byte[] arr : list) {
      System.arraycopy(arr, 0, result, offset, arr.length);
      offset += arr.length;
    }
    return result;
  }
}
