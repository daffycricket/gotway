package android.nla.org.gotway.ble;

public class Util
{
  public static String bytes2HexStr(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer("[");
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayOfByte.length)
      {
        localStringBuffer.replace(localStringBuffer.length() - 2, localStringBuffer.length(), "]");
        return localStringBuffer.toString();
      }
      localStringBuffer.append(String.format("%02x", new Object[] { Integer.valueOf(paramArrayOfByte[i] & 0xFF) }) + ", ");
      i += 1;
    }
  }
  
  public static int[] bytes2ints(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = new int[paramArrayOfByte.length];
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayOfByte.length) {
        return arrayOfInt;
      }
      paramArrayOfByte[i] &= 0xFF;
      i += 1;
    }
  }
}