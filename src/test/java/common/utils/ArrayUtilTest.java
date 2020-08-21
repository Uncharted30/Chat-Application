package common.utils;

import static org.junit.Assert.*;

import common.CommonConstants;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ArrayUtilTest {

  @Test
  public void concat() {
    byte[] arr = new byte[21];
    arr[10] = CommonConstants.SPACE_BYTE;
    assertArrayEquals(arr, ArrayUtil.concat(new ArrayList<>(Arrays.asList(new byte[10], new byte[10]))));
    assertArrayEquals(new byte[10], ArrayUtil.concat(new ArrayList<>(Arrays.asList(new byte[10]))));
    assertArrayEquals(new byte[0], ArrayUtil.concat(new ArrayList<>()));
  }
}