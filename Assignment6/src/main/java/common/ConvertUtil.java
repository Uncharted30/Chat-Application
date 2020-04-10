package common;

import java.nio.ByteBuffer;

public class ConvertUtil {
  static byte booleanToByte(boolean elem) {
    return (byte) (elem ? 1 : 0);
  }
  static boolean byteToBoolean(byte elem) {
    return elem != 0;
  }

  static byte[] intToByteArray(int elem) {
    return ByteBuffer.allocate(4).putInt(elem).array();
  }

  static int byteArrayToInt(byte[] elem) {
    if (elem == null || elem.length < 4) {
      return 0xDEADBEEF;
    }
    return ByteBuffer.wrap(elem, 0, 4).getInt();
  }
}
