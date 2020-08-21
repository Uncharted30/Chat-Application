package common.utils;

import common.CommonConstants;
import java.util.Arrays;
import java.util.List;

/**
 * The type Array util.
 */
public class ArrayUtil {

  /**
   * Concat byte arrays into one byte array, use a space as separator.
   *
   * @param list the list of byte array to be concatenated
   * @return the result byte array
   */
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
