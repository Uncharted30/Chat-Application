package common.utils;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import org.junit.Test;

public class ConvertUtilTest {

  @Test
  public void booleanToByte() {
    assertEquals((byte)0, ConvertUtil.booleanToByte(false));
    assertEquals((byte)1, ConvertUtil.booleanToByte(true));
  }

  @Test
  public void byteToBoolean() {
    assertTrue(ConvertUtil.byteToBoolean((byte) 1));
    assertFalse(ConvertUtil.byteToBoolean((byte) 0));
  }

  @Test
  public void intToByteArray() {
    assertArrayEquals(ByteBuffer.allocate(4).putInt(10).array(), ConvertUtil.intToByteArray(10));
  }

  @Test
  public void byteArrayToInt() {
    assertEquals(10, ConvertUtil.byteArrayToInt(ByteBuffer.allocate(4).putInt(10).array()));
    assertEquals(0xDEADBEEF, ConvertUtil.byteArrayToInt(new byte[]{1, 2}));
    assertEquals(0xDEADBEEF, ConvertUtil.byteArrayToInt(null));
  }
}