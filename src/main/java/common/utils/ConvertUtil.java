package common.utils;

import java.nio.ByteBuffer;

/**
 * The type Convert util.
 */
public class ConvertUtil {

  /**
   * Boolean to byte.
   *
   * @param elem the elem to be converted
   * @return the byte
   */
  public static byte booleanToByte(boolean elem) {
    return (byte) (elem ? 1 : 0);
  }

  /**
   * Byte to boolean.
   *
   * @param elem the elem to be converted
   * @return the boolean
   */
  public static boolean byteToBoolean(byte elem) {
    return elem != 0;
  }

  /**
   * Int to byte array.
   *
   * @param elem the elem to be converted
   * @return the byte [ ]
   */
  public static byte[] intToByteArray(int elem) {
    return ByteBuffer.allocate(4).putInt(elem).array();
  }

  /**
   * Byte array to int.
   *
   * @param elem the elem to be converted
   * @return the int
   */
  public static int byteArrayToInt(byte[] elem) {
    if (elem == null || elem.length < 4) {
      return 0xDEADBEEF;
    }
    return ByteBuffer.wrap(elem, 0, 4).getInt();
  }
}
